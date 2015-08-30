package com.scrape.dao

import scala.collection.mutable.Buffer
import javax.annotation.Resource
import scala.beans.BeanProperty
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import scala.collection.JavaConversions._
import org.springframework.stereotype.Component

case class DBEntry(url: String, hash: String)
/**
 * @author leheli
 */
@Component
class RedditDao {

  @Resource
  @BeanProperty
  var namedJdbc: NamedParameterJdbcTemplate = _

  def readUnprocessedLine(): Buffer[DBEntry] = {
        val sql="select id,url from post where processed=0 ; "
        val mapper:DBEntryMapper=new DBEntryMapper()
        val res:java.util.List[DBEntry]=namedJdbc.query(sql, mapper)
        res
  }
  
  
  class DBEntryMapper extends RowMapper[DBEntry] {
    def mapRow(rs: ResultSet, rNum: Int): DBEntry = {
       DBEntry(rs.getString("url"),rs.getString("id"))
    }
  }
}