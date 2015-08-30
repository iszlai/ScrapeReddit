package com.scrape.actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import com.scrape.dao.RedditDao
import com.scrape.dao.DBEntry
import com.scrape.DBStatusMsg
import java.math.BigInteger

/**
 * @author leheli
 */
class DBUpdater (dao:RedditDao)extends Actor with ActorLogging {
  
  
  
  def receive: Actor.Receive = {
    case DBStatusMsg(db:DBEntry,status) =>{
      log.info("received msg "+db.hash+" status "+status)
    }
     case _ => log.info("Error: message not recognized")
  }
}