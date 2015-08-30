package com.scrape.actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import com.scrape.FileWriterMsg
import com.scrape.dao.DBEntry
import com.scrape.PersistToFile
import com.scrape.DBStatusMsg
import com.scrape.dao.DBEntry

/**
 * @author leheli
 * 
 * 0|UNPROCESSED
  1|PROCESSED
  2|FORBIDDEN
  3|TIMEOUT
 * 
 * 
 */
class FileWriter(dbUpdate:ActorRef) extends Actor with ActorLogging{
  def receive= {
    case FileWriterMsg(db:DBEntry,data:String) =>{
        PersistToFile.persist(db.hash, data)
        dbUpdate ! DBStatusMsg(db,1)
        log.info("Written to file:" +db.hash)
    }
    
    case _ => log.info("Error: message not recognized")
  }
}