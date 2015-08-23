package com.scrape.dao

import org.springframework.context.annotation.Configuration
import javax.annotation.Resource
import scala.beans.BeanProperty
import javax.sql.DataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.apache.commons.dbcp.BasicDataSource

@Configuration
class DBConfig {
  @Bean
  def getDS(): DataSource = {
    val ds: BasicDataSource = new BasicDataSource
    ds.setDriverClassName("org.sqlite.JDBC")
    ds.setUrl("jdbc:sqlite:reddit.db")
    ds.setInitialSize(1)
    ds.setMaxActive(1)
    ds.setMaxIdle(1)
    ds.setPoolPreparedStatements(true)
    ds
  }

  @Bean
  def namedJdbc(): NamedParameterJdbcTemplate = {
    new NamedParameterJdbcTemplate(getDS())
  }

}
