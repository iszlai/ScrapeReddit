package com.scrape

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import com.scrape.actors.DBUpdater
import com.scrape.actors.FileWriter
import com.scrape.actors.HTMLFetcher
import com.scrape.actors.Prettyfy
import com.scrape.dao.DBEntry
import com.scrape.dao.RedditDao
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import akka.event.Logging
import akka.routing.RoundRobinRouter
import akka.routing.RoundRobinPool

/**
 * @author leheli
 */
object Main extends App {
  val ctx = new AnnotationConfigApplicationContext();
  ctx.scan("com.scrape")
  ctx.refresh();

  val dao = ctx.getBean(classOf[RedditDao])

  val system = ActorSystem("System")
  val DBUpdate = system.actorOf(Props(new DBUpdater(dao)).withRouter(RoundRobinPool(nrOfInstances=100)), name = "DBUpdater")
  val fileWriter = system.actorOf(Props(new FileWriter(DBUpdate)), name = "FileWriter")
  val pretty = system.actorOf(Props(new Prettyfy(fileWriter)).withRouter(RoundRobinPool(nrOfInstances=100)), name = "Prettyfy")
  val HTMLFetcher = system.actorOf(Props(new HTMLFetcher(pretty, DBUpdate)).withRouter(RoundRobinPool(nrOfInstances=100)), name = "HTMLFetcher")
  println("Starting up");
  val lines = dao.readUnprocessedLine()
  println("database read")
  for (i <- lines) {
    HTMLFetcher ! new FetchHtmlMsg(i)
  }
  println("Messages sent")

}