package com.scrape
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import java.util.List
import com.gargoylesoftware.htmlunit.html.DomNode
import com.sun.org.apache.xalan.internal.xsltc.DOM
import scala.collection.JavaConversions._


object test {
   val webclient: WebClient = new WebClient();    //> webclient  : com.gargoylesoftware.htmlunit.WebClient = com.gargoylesoftware.
                                                  //| htmlunit.WebClient@503d687a
    val options = webclient.getOptions            //> options  : com.gargoylesoftware.htmlunit.WebClientOptions = com.gargoylesoft
                                                  //| ware.htmlunit.WebClientOptions@6d4d66d2
    options.setJavaScriptEnabled(false)
    val page: HtmlPage = webclient.getPage("http://www.reddit.com/r/programming")
                                                  //> May 24, 2015 6:12:30 PM com.gargoylesoftware.htmlunit.html.InputElementFacto
                                                  //| ry createElementNS
                                                  //| INFO: Bad input type: "email", creating a text input
                                                  //| page  : com.gargoylesoftware.htmlunit.html.HtmlPage = HtmlPage(http://www.re
                                                  //| ddit.com/r/programming)@172678484
    val posts:List[DomNode] = page.getByXPath("//*[@id=\"siteTable\"]/div/div/p").asInstanceOf[List[DomNode]]
                                                  //> posts  : java.util.List[com.gargoylesoftware.htmlunit.html.DomNode] = [HtmlP
                                                  //| aragraph[<p class="title">], HtmlParagraph[<p class="tagline">], HtmlParagra
                                                  //| ph[<p class="title">], HtmlParagraph[<p class="tagline">], HtmlParagraph[<p 
                                                  //| class="title">], HtmlParagraph[<p class="tagline">], HtmlParagraph[<p class=
                                                  //| "title">], HtmlParagraph[<p class="tagline">], HtmlParagraph[<p class="title
                                                  //| ">], HtmlParagraph[<p class="tagline">], HtmlParagraph[<p class="title">], H
                                                  //| tmlParagraph[<p class="tagline">], HtmlParagraph[<p class="title">], HtmlPar
                                                  //| agraph[<p class="tagline">], HtmlParagraph[<p class="title">], HtmlParagraph
                                                  //| [<p class="tagline">], HtmlParagraph[<p class="title">], HtmlParagraph[<p cl
                                                  //| ass="tagline">], HtmlParagraph[<p class="title">], HtmlParagraph[<p class="t
                                                  //| agline">], HtmlParagraph[<p class="title">], HtmlParagraph[<p class="tagline
                                                  //| ">], HtmlParagraph[<p class="title">], HtmlParagraph[<p class="tagline">], H
                                                  //| tmlParagraph[<p class="t
                                                  //| Output exceeds cutoff limit.
  //  val posts2:List[DomNode] = page.getByXPath("//*[@id=\"siteTable\"]/div/div").asInstanceOf[List[DomNode]]
  //  val mpd=posts2 map(x => getAstext(x))
  //  mpd.get(0)
  
  for ( i<- posts){
  //	println(getTitle(i)+" "+getUrl(i))
  println(i)
   println(i.asXml())
  }                                               //> HtmlParagraph[<p class="title">]
                                                  //| <p class="title">
                                                  //|   <a class="title may-blank " href="https://www.youtube.com/watch?v=YX40hbAH
                                                  //| x3s" tabindex="1">
                                                  //|     The N vs NP problem, explained
                                                  //|   </a>
                                                  //|   <span class="domain">
                                                  //|     (
                                                  //|     <a href="/domain/youtube.com/">
                                                  //|       youtube.com
                                                  //|     </a>
                                                  //|     )
                                                  //|   </span>
                                                  //| </p>
                                                  //| 
                                                  //| HtmlParagraph[<p class="tagline">]
                                                  //| <p class="tagline">
                                                  //|   submitted 
                                                  //|   <time title="Sun May 24 04:05:40 2015 UTC" datetime="2015-05-24T04:05:40+0
                                                  //| 0:00" class="live-timestamp">
                                                  //|     12 hours ago
                                                  //|   </time>
                                                  //|   by 
                                                  //|   <a href="http://www.reddit.com/user/afffsdd" class="author may-blank id-t2
                                                  //| _bu4hy">
                                                  //|     afffsdd
                                                  //|   </a>
                                                  //|   <span class="userattrs">
                                                  //|   </span>
                                                  //| </p>
                                                  //| 
                                                  //| HtmlParagraph[<p class="title">]
                                                  //| <p class="title">
                                                  //|   <a class="title may-blank " href="http://security.stackexchange.com/questi
                                                  //| ons/45963/diffie-hellman-key-exchange-in-plain-
                                                  //| Output exceeds cutoff limit.
    
    
    def getTitle(node:DomNode):String=node.asText()
                                                  //> getTitle: (node: com.gargoylesoftware.htmlunit.html.DomNode)String
  def getUrl(node:DomNode):String=node.getAttributes.getNamedItem("href").getNodeValue
                                                  //> getUrl: (node: com.gargoylesoftware.htmlunit.html.DomNode)String
  def getAstext(node:DomNode):List[DomNode]=node.getByXPath("p").asInstanceOf[List[DomNode]]
                                                  //> getAstext: (node: com.gargoylesoftware.htmlunit.html.DomNode)java.util.List
                                                  //| [com.gargoylesoftware.htmlunit.html.DomNode]

}