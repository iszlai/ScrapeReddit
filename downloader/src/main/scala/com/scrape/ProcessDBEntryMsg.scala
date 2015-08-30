package com.scrape

import com.scrape.dao.DBEntry

/**
 * @author leheli
 */
case class FetchHtmlMsg(entry:DBEntry) 
case class ProcessHtmlMsg(entry:DBEntry,contets:String)
case class DBStatusMsg(entry:DBEntry,status:Int)
case class FileWriterMsg(entry:DBEntry,content:String)
