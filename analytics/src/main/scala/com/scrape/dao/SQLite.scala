package com.scrape.dao
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import com.scrape.model.Post
import com.scrape.model.PostDTO
import com.scrape.model.PostDTO
import com.scrape.model.Votes
import scala.collection.mutable.ListBuffer
import com.scrape.model.Votes
import org.slf4j.LoggerFactory
import com.scrape.model.PostDTO
import com.scrape.model.TopPostCategory
import com.scrape.model.UserStat
import com.scrape.model.UserStat
import com.scrape.model.UserStat
import com.scrape.model.CategoryStat

class SQLite {
  val log = LoggerFactory.getLogger(classOf[SQLite])
  var connection: Connection = _;
  val createPOST: String = """ CREATE TABLE IF NOT EXISTS POST(
    | ID TEXT PRIMARY KEY NOT NULL, 
    | TITLE TEXT NOT NULL, 
    | URL TEXT NOT NULL, 
    | USER TEXT NOT NULL,
    | SRC TEXT NOT NULL ); """.stripMargin.replaceAll("\n", " ")

  val creatVOTES: String = """ CREATE TABLE IF NOT EXISTS VOTES(
| ID TEXT NOT NULL,
| UP INT NOT NULL,
| DOWN INT NOT NULL,
| SCORE NOT NULL,
| sqltime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL );""".stripMargin.replaceAll("\n", " ")

  init()

  def init() {
    //println("init called" + createPOST + "\n" + creatVOTES)
    Class.forName("org.sqlite.JDBC")
    try {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:reddit.db")
      val stmt = connection.createStatement()
      stmt.executeUpdate(createPOST);
      stmt.executeUpdate(creatVOTES);
    } catch {
      case e: SQLException =>
        // if the error message is "out of memory",
        // it probably means no database file is found
        System.err.println(e.getMessage)
    }
  }

  def checkIfRowExists(id: String): Boolean = {
    val stmt = connection.createStatement();
    val result = stmt.executeQuery("select title from post where id='" + id + "'");
    if (!result.isBeforeFirst()) false else true;
  }

  def getPost(id: String): Option[PostDTO] = {
    var res: Option[PostDTO] = None
    val stmt = connection.createStatement();
    val resultSet = stmt.executeQuery("select id,title,url,user,src from post where id='" + id + "'");
    while (resultSet.next()) {
      val id = resultSet.getString("id")
      val title = resultSet.getString("title")
      val url = resultSet.getString("url")
      val user = resultSet.getString("user")
      val src = resultSet.getString("src")
      res = Some(PostDTO(id, title, url, user, src))
    }
    res
  }

  def getVotes(id: String): ListBuffer[Votes] = {
    val results: ListBuffer[Votes] = new ListBuffer
    val stmt = connection.createStatement();
    val sql = s"select up,down,score,sqltime from votes where id='$id' order by sqltime"
    log.debug(s"Getting votes SQL:[$sql]")
    val resultSet = stmt.executeQuery(sql);
    while (resultSet.next()) {
      val up = resultSet.getInt("up")
      val down = resultSet.getInt("down")
      val score = resultSet.getInt("score")
      val time = resultSet.getString("sqltime")
      results += Votes(up, down, score, time)
    }

    results
  }

  def getPosts(): ListBuffer[PostDTO] = {
    val results: ListBuffer[PostDTO] = new ListBuffer
    val stmt = connection.createStatement();
    val resultSet = stmt.executeQuery("select id,title,url,user,src from post");
    while (resultSet.next()) {
      val id = resultSet.getString("id")
      val title = resultSet.getString("title")
      val url = resultSet.getString("url")
      val user = resultSet.getString("user")
      val src = resultSet.getString("src")
      results += PostDTO(id, title, url, user, src)
    }
    results
  }

  def topPostPerCategory(): ListBuffer[TopPostCategory] = {
    val results: ListBuffer[TopPostCategory] = new ListBuffer
    val stmt = connection.createStatement();
    val resultSet = stmt.executeQuery("select max(v.score) as m," +
      " p.src as src, p.title as title, p.url as url ,p.user as user" +
      " from votes as v," +
      "post as p " +
      "where p.id=v.id group by p.src order by m desc;")
    while (resultSet.next()) {
      val max = resultSet.getInt("m")
      val src = resultSet.getString("src")
      val title = resultSet.getString("title")
      val url = resultSet.getString("url")
      val user = resultSet.getString("user")

      results += TopPostCategory(max, src, title, url, user)
    }
    results
  }

  def userStats(): ListBuffer[UserStat] = {
    val results: ListBuffer[UserStat] = new ListBuffer
    val stmt = connection.createStatement();
    val resultSet = stmt.executeQuery("select count(*) as nr, sum(v.score) as m, p.user as user" +
      " from votes as v,post as p" +
      " where p.id=v.id" +
      " group by p.user order by m desc limit 10;")
    while (resultSet.next()) {
      val count = resultSet.getInt("nr")
      val sumScore = resultSet.getInt("m")
      val user = resultSet.getString("user")
      results += UserStat(count, sumScore, user)
    }
    results
  }

  def categoryStat(): ListBuffer[CategoryStat] = {
    val results: ListBuffer[CategoryStat] = new ListBuffer
    val stmt = connection.createStatement();
    val resultSet = stmt.executeQuery("select p.src as category ,round(avg(v.score)) as avgScore" +
      " from post as p, votes as v " +
      " where p.id=v.id " +
      " group by p.src order by avgScore desc;")
    while (resultSet.next()) {
      val avgScore = resultSet.getInt("avgScore")
      val category = resultSet.getString("category")
      results += CategoryStat(avgScore, category)
    }
    results
  }

  def lastRun(): String = {
    val stmt = connection.createStatement();
    val resultSet = stmt.executeQuery("select sqltime from votes order by sqltime desc limit 1;")
    val sqltime = resultSet.getString("sqltime")
    sqltime
  }

  def close() {
    try {
      if (connection != null)
        connection.close()
    } catch {
      case e: SQLException =>
        // connection close failed.
        System.err.println(e)
    }
  }

}