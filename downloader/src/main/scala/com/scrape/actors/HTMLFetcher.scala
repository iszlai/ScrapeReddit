package com.scrape.actors

/**
 * @author leheli
 */

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Actor
import com.scrape.dao.DBEntry
import com.scrape.FetchHtmlMsg
import com.scrape.ProcessHtmlMsg
import com.scrape.DBStatusMsg

class HTMLFetcher(pretty: ActorRef, dbUpdate: ActorRef) extends Actor with ActorLogging {
  val webclient: WebClient = new WebClient();
  val options = webclient.getOptions
  options.setJavaScriptEnabled(false)

  def receive = {

    case FetchHtmlMsg(db: DBEntry) => {
      try {
        val page: HtmlPage = webclient.getPage(db.url)
        val html = page.asXml()
        pretty ! ProcessHtmlMsg(db, html)
      } catch {

        case e: Exception => {
          log.info("handled exception ")
          dbUpdate ! DBStatusMsg(db, 3)

        }
      }

    }

    case _ => log.info("Error: message not recognized")
  }
}