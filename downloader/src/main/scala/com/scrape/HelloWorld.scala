package com.scrape

import scala.beans.BeanProperty
import javax.annotation.Resource
import javax.sql.DataSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer
/**
 * @author leheli
 */
class HelloWorld(foo:String) {
  
  @Resource
  @BeanProperty
  var namedJdbc:NamedParameterJdbcTemplate=_
  
  def getfoo():Buffer[String]={
    val rm:URLRowMapper=new URLRowMapper
    val res:java.util.List[String]=namedJdbc.query("select url from post",rm );
    res.asScala
  }
  
  class URLRowMapper extends RowMapper[String] {
    def mapRow(rs: ResultSet, rowNum: Int): String = {
      rs.getString("url");
    }
  }
}