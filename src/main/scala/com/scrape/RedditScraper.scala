package com.scrape

import com.gargoylesoftware.htmlunit.html.DomNode
import com.gargoylesoftware.htmlunit.html.HtmlPage
import scala.xml.XML
import java.util.List


object RedditScraper {

  def getPosts(page: HtmlPage): List[DomNode] = getXPath(page, "//*[@id=\"siteTable\"]/div/div/p[1]/a")
  def getAutors(page: HtmlPage): List[DomNode] = getXPath(page, "//*[@id=\"siteTable\"]/div/div/p[2]/a")
  def getUpVotes(page: HtmlPage): List[DomNode] = getXPath(page, "//*[@id=\"siteTable\"]/div/div/div[4]")
  def getScoreVotes(page: HtmlPage): List[DomNode] = getXPath(page, "//*[@id=\"siteTable\"]/div/div[1]/div[3]")
  def getDownVotes(page: HtmlPage): List[DomNode] = getXPath(page, "//*[@id=\"siteTable\"]/div/div[1]/div[2]")
  def getXPath(page: HtmlPage, path: String): List[DomNode] = page.getByXPath(path).asInstanceOf[List[DomNode]]

  
  def getAuthor(node: DomNode): String = node.asText()
  def getTitle(node: DomNode): String = node.asText()
  def getUrl(node: DomNode): String = node.getAttributes.getNamedItem("href").getNodeValue
  def getVotes(node: DomNode): Int = {
    val asStringXML = node.asXml()
    val asXMl = XML.loadString(asStringXML)
    toInt(asXMl.text.trim)
  }

  def toInt(s: String): Int = {
    try {
      s.toInt
    } catch {
      case e: Exception => 0
    }
  }

  def hash(s: String) = {
    val m = java.security.MessageDigest.getInstance("MD5")
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(1, m.digest()).toString(16)
  }
}