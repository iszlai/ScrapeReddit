package hello

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

@RestController
class GreetingController {

  val log = LoggerFactory.getLogger(classOf[GreetingController])

  @Value("${scrape.test}")
  var someValue: String = _

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
    val db: SQLite = new SQLite
    val res = db.getPost(id).getOrElse(PostDTO("mockid", "mock", "mock", "mock", "mock"))
    db.close()
    res
  }

  @RequestMapping(value = Array("/posts"))
  def getPost(): java.util.List[PostDTO] = {
    val db: SQLite = new SQLite
    val res = db.getPosts()
    db.close()
    res
  }

  @RequestMapping(value = Array("/votes"))
  def getVotes(@RequestParam(value = "id", required = false, defaultValue = "") id: String): java.util.List[Votes] = {
    val db: SQLite = new SQLite
    val res: java.util.List[Votes] = db.getVotes(id)
    db.close()
    log.info(res(0).toString())
    res
  }

}
