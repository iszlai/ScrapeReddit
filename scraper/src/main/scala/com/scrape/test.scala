package com.scrape

import scala.collection.JavaConversions._
import scala.collection.JavaConversions._

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.scrape.db.Database
import com.scrape.model.Post
import com.scrape.model.Votes

object Hi {

  def main(args: Array[String]) {
    val subreddits = List("math", "programming", "javascript", "haskell", "java", "golang", "scala", "distributed", "elixir", "erlang", "fsharp", "GraphicsProgramming")
    for (i <- subreddits) {
      println("-" * 10)
      val date: String = new java.util.Date().toString;
      println(s"[$date]Crawling subreddit: $i")
      println("-" * 10)
      crawlSubreddit(i)
      Thread.sleep(500);
    }

    println("-" * 10)
    val date: String = new java.util.Date().toString;
    println(s"[$date]Crawling done ")
    println("-" * 10)

  }

  def crawlSubreddit(subreddit: String) {
    val webclient: WebClient = new WebClient();
    val options = webclient.getOptions
    options.setJavaScriptEnabled(false)
    val page: HtmlPage = webclient.getPage("http://www.reddit.com/r/" + subreddit)
    val posts = RedditScraper.getPosts(page)
    val author = RedditScraper.getAutors(page)
    val score = RedditScraper.getScoreVotes(page)
    val upvotes = RedditScraper.getUpVotes(page)
    val dislikes = RedditScraper.getDownVotes(page)
    val db = new Database();
    for (i <- 0 until posts.length) {

      val vote = Votes(
        RedditScraper.getVotes(upvotes(i)),
        RedditScraper.getVotes(dislikes(i)),
        RedditScraper.getVotes(score(i)))

      val post = Post(
        RedditScraper.getTitle(posts(i)),
        RedditScraper.getUrl(posts(i)),
        RedditScraper.getAuthor(author(i)),
        subreddit,
        vote)
      if (!db.checkIfRowExists(RedditScraper.hash(post.url))) {
        db.insertPost(post)
      }
      db.insertVote(RedditScraper.hash(post.url), vote)
    }

    db.close();
  }

}
