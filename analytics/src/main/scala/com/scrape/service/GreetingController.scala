package com.scrape.service

import java.util.concurrent.atomic.AtomicInteger
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.scrape.model.Greeting
import org.springframework.beans.factory.annotation.Value
import com.scrape.model.PostDTO
import com.scrape.dao.SQLite
import com.scrape.dao.SQLite
import com.scrape.model.PostDTO
import scala.collection.mutable.ListBuffer
import com.scrape.model.Votes
import scala.collection.JavaConversions._
import org.slf4j.LoggerFactory
import com.scrape.model.TopPostCategory
import com.scrape.model.TopPostCategory
import com.scrape.model.UserStat
import com.scrape.model.CategoryStat
import javax.annotation.Resource
import scala.beans.BeanProperty

@RestController
class GreetingController {

  val log = LoggerFactory.getLogger(classOf[GreetingController])

  log.info("!!!!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~~~~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!")
  
  @Value("${scrape.test}")
  var someValue: String = _

  @Resource
  @BeanProperty
  var db: SQLite = _

  private val template = "Hello, %s!"
  private var counter: AtomicInteger = new AtomicInteger

  @RequestMapping(value = Array("/greeting"))
  def greeting(@RequestParam(value = "name", required = false, defaultValue = "World") name: String): Greeting = {
    Greeting(counter.incrementAndGet(), String.format(template, name));
  }

  @RequestMapping(value = Array("/test"))
  def greeting(): String = {
    someValue
  }

  @RequestMapping(value = Array("/post"))
  def getPost(@RequestParam(value = "id", required = false, defaultValue = "") id: String): PostDTO = {
    val res = db.getPost(id).getOrElse(PostDTO("mockid", "mock", "mock", "mock", "mock"))
    res
  }

  @RequestMapping(value = Array("/posts"))
  def getPost(): java.util.List[PostDTO] = {
    val res = db.getPosts()
    res
  }

  @RequestMapping(value = Array("/votes"))
  def getVotes(@RequestParam(value = "id", required = false, defaultValue = "") id: String): java.util.List[Votes] = {
    val res: java.util.List[Votes] = db.getVotes(id)
    log.info(res(0).toString())
    res
  }

  @RequestMapping(value = Array("/topPostCategory"))
  def getTopPostCategory(): java.util.List[TopPostCategory] = {
    val res: java.util.List[TopPostCategory] = db.topPostPerCategory()
    log.info("Top posts fetched")
    res
  }

  @RequestMapping(value = Array("/topUser"))
  def topUser(): java.util.List[UserStat] = {
    val res: java.util.List[UserStat] = db.userStats()
    log.info("Top users fetched")
    res
  }

  @RequestMapping(value = Array("/categoryStat"))
  def categoryStats(): java.util.List[CategoryStat] = {
    val res: java.util.List[CategoryStat] = db.categoryStat()
    log.info("Category stats fetched")
    res
  }

  @RequestMapping(value = Array("/lastRun"))
  def lastRun(): String = {
    val res: String = "\"" + db.lastRun() + "\""
    log.info("lastRun stats fetched")
    res
  }

}
