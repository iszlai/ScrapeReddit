package com.scrape.db

import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Connection
import com.scrape.model.Post
import com.scrape.model.Votes
import com.scrape.RedditScraper

class Database {

  var connection: Connection = _;
  val createPOST: String = """ CREATE TABLE IF NOT EXISTS POST(
    | ID TEXT PRIMARY KEY NOT NULL, 
    | TITLE TEXT NOT NULL, 
    | URL TEXT NOT NULL, 
    | USER TEXT NOT NULL ); """.stripMargin.replaceAll("\n", " ")

  val creatVOTES: String = """ CREATE TABLE IF NOT EXISTS VOTES(
| ID TEXT PRIMARY KEY NOT NULL,
| UP INT NOT NULL,
| DOWN INT NOT NULL,
| SCORE NOT NULL );""".stripMargin.replaceAll("\n", " ")
  init()

  def init() {
    println("init called" + createPOST + "\n" + creatVOTES)
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
    } finally {
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

  def insertPost(post: Post) {
    val sql = "INSERT INTO POST VALUES (?, ?,?,?);"
    val ps = connection.prepareStatement(sql)
    ps.setString(0, RedditScraper.hash(post.url));
    ps.setString(1, post.title);
    ps.setString(2, post.url);
    ps.setString(3, post.user);
    val nrOfChange = ps.executeUpdate();
  }

  def insertVote(id: String, vote: Votes) {
    val sql = "INSERT INTO VOTES VALUES (?, ?,?,?);"
    val ps = connection.prepareStatement(sql)
    ps.setString(0, id);
    ps.setInt(1, vote.up);
    ps.setInt(1, vote.down);
    ps.setInt(3, vote.score);
    val nrOfChange = ps.executeUpdate();
  }

}