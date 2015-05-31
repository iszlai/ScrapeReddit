package com.scrape

import com.gravity.goose.Goose
import com.gravity.goose.Configuration

object Main {
def main(args: Array[String]): Unit = {
  val goose=new Goose(new Configuration)
  val art=goose.extractContent("http://kylepurdon.com/python/ruby/golang/development/2015/05/30/python-ruby-and-go-a-command-line-application-comparison.html")
  println(art.title)
//  println(art.cleanedArticleText)
}
}