package com.scrape

import scala.collection.JavaConversions._
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.scrape.model.Post
import com.scrape.model.Votes
import collection.JavaConversions._
import com.scrape.db.Database

object Hi {

  def main(args: Array[String]) {
    val webclient: WebClient = new WebClient();
    val options = webclient.getOptions
    options.setJavaScriptEnabled(false)
    val page: HtmlPage = webclient.getPage("http://www.reddit.com/r/programming")
    val posts = RedditScraper.getPosts(page)
    val author = RedditScraper.getAutors(page)
    val score = RedditScraper.getScoreVotes(page)
    val upvotes = RedditScraper.getUpVotes(page)
    val dislikes = RedditScraper.getDownVotes(page)
    for (i <- 0 until posts.length) {

      val vote = Votes(
        RedditScraper.getVotes(upvotes(i)),
        RedditScraper.getVotes(dislikes(i)),
        RedditScraper.getVotes(score(i)))

      val post = Post(
        RedditScraper.getTitle(posts(i)),
        RedditScraper.getUrl(posts(i)),
        RedditScraper.getAuthor(author(i)),
        vote)
      //println(getVotes(score(i)))
      println(RedditScraper.hash(post.url))
    }

    val db=new Database();
  }

}
