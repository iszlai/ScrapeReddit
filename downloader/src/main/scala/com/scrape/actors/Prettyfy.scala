package com.scrape.actors

import com.scrape.ProcessHtmlMsg
import com.scrape.dao.DBEntry
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import de.l3s.boilerpipe.extractors.ArticleExtractor
import com.scrape.FileWriterMsg

/**
 * @author leheli
 */

class Prettyfy(fileWriter:ActorRef) extends Actor with ActorLogging{
    val extractor=ArticleExtractor.getInstance
 
  
  def receive = {
      
      
    case ProcessHtmlMsg(entry:DBEntry,html:String) => {
      val article =extractor.getText(html)
      log.info(entry.hash)
      fileWriter ! FileWriterMsg(entry,article)
    }
  
    case _ => log.info("Error: message not recognized")
  }
}