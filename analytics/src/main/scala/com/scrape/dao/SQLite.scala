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
import javax.annotation.Resource
import scala.beans.BeanProperty
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.util.HashMap
import java.sql.ResultSet
import org.springframework.stereotype.Component

@Component
class SQLite {
  val log = LoggerFactory.getLogger(classOf[SQLite])

  @Resource
  @BeanProperty
  var namedJdbc: NamedParameterJdbcTemplate = _

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

  def checkIfRowExists(id: String): Boolean = {
    val query: java.util.Map[String, Object] = new HashMap();
    query.put("id", id);
    val mapper: StringMapper = new StringMapper("title");
    val res = namedJdbc.query("select title from post where id=:id", query, mapper);
    return (res.size() > 0)
  }

  def getPost(id: String): Option[PostDTO] = {
    val query: java.util.Map[String, Object] = new HashMap()
    query.put("id", id)
    val mapper: PostDTOMapper = new PostDTOMapper();
    val sql = "select id,title,url,user,src from post where id=:id"
    val res: java.util.List[PostDTO] = namedJdbc.query(sql, query, mapper)
    Some(res.get(0))
  }

  def getVotes(id: String): java.util.List[Votes] = {
    val sql = "select up,down,score,sqltime from votes where id=:id order by sqltime"
    log.debug(s"Getting votes SQL:[$sql]")
    val query: java.util.Map[String, Object] = new HashMap()
    query.put("id", id)
    val mapper: VotesMapper = new VotesMapper()
    namedJdbc.query(sql, query, mapper)
  }

  def getPosts(): java.util.List[PostDTO] = {
    val sql = "select id,title,url,user,src from post"
    val mapper = new PostDTOMapper()
    val query: java.util.Map[String, Object] = new HashMap()
    val res = namedJdbc.query(sql, query, mapper)
    res
  }

  def topPostPerCategory(): java.util.List[TopPostCategory] = {
    val sql = "select max(v.score) as m," +
      " p.src as src, p.title as title, p.url as url ,p.user as user" +
      " from votes as v," +
      "post as p " +
      "where p.id=v.id group by p.src order by m desc;"
    val mapper = new TopPostCategoryMapper()
    val query: java.util.Map[String, Object] = new HashMap()
    namedJdbc.query(sql, query, mapper)
  }

  def userStats(): java.util.List[UserStat] = {
    val sql = "select count(*) as nr, sum(v.score) as m, p.user as user" +
      " from votes as v,post as p" +
      " where p.id=v.id" +
      " group by p.user order by m desc limit 10;"
    val mapper = new UserStatMapper()
    val query: java.util.Map[String, Object] = new HashMap()
    namedJdbc.query(sql, query, mapper)
  }

  def categoryStat(): java.util.List[CategoryStat] = {
    val sql = "select p.src as category ,round(avg(v.score)) as avgScore" +
      " from post as p, votes as v " +
      " where p.id=v.id " +
      " group by p.src order by avgScore desc;"
    val mapper = new CategoryStatMapper()
    val query: java.util.Map[String, Object] = new HashMap()
    namedJdbc.query(sql, query, mapper)
  }

  def lastRun(): String = {
    val sql = "select sqltime from votes order by sqltime desc limit 1;"
    val mapper = new StringMapper("sqltime")
    val query: java.util.Map[String, Object] = new HashMap()
    namedJdbc.query(sql, query, mapper).get(0)
  }

}