package com.scrape.dao

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import com.scrape.model.PostDTO
import com.scrape.model.Votes
import com.scrape.model.TopPostCategory
import com.scrape.model.UserStat
import com.scrape.model.CategoryStat

class Mappers {

}

class StringMapper(query:String) extends RowMapper[String] {
  def mapRow(rs: ResultSet, rNum: Int): String = {
    rs.getString(query)
  }
}

class PostDTOMapper extends RowMapper[PostDTO] {
  def mapRow(resultSet: ResultSet, rNum: Int): PostDTO = {
    val id = resultSet.getString("id")
    val title = resultSet.getString("title")
    val url = resultSet.getString("url")
    val user = resultSet.getString("user")
    val src = resultSet.getString("src")
    PostDTO(id, title, url, user, src)
  }
}

class VotesMapper extends RowMapper[Votes] {
  def mapRow(resultSet: ResultSet, rNum: Int): Votes = {
    val up = resultSet.getInt("up")
    val down = resultSet.getInt("down")
    val score = resultSet.getInt("score")
    val time = resultSet.getString("sqltime")
    Votes(up, down, score, time)
  }
}

class TopPostCategoryMapper extends RowMapper[TopPostCategory] {
  def mapRow(resultSet: ResultSet, rNum: Int): TopPostCategory = {
    val max = resultSet.getInt("m")
    val src = resultSet.getString("src")
    val title = resultSet.getString("title")
    val url = resultSet.getString("url")
    val user = resultSet.getString("user")
    TopPostCategory(max, src, title, url, user)
  }
}

class UserStatMapper extends RowMapper[UserStat] {
  def mapRow(resultSet: ResultSet, rNum: Int): UserStat = {
    val count = resultSet.getInt("nr")
    val sumScore = resultSet.getInt("m")
    val user = resultSet.getString("user")
    UserStat(count, sumScore, user)
  }
}

  class CategoryStatMapper extends RowMapper[CategoryStat] {
    def mapRow(resultSet: ResultSet, rNum: Int): CategoryStat = {
      val avgScore = resultSet.getInt("avgScore")
      val category = resultSet.getString("category")
      CategoryStat(avgScore, category)
    }
  }