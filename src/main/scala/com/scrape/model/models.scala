package com.scrape.model

case class Votes(up: Int, down: Int, score: Int)
case class Post(title: String, url: String, user: String,src:String ,vote: Votes)