package com.scrape.model

import scala.beans.BeanProperty

case class Post(@BeanProperty title: String, 
                @BeanProperty url: String,
                @BeanProperty user: String,
                @BeanProperty src: String,
                @BeanProperty vote: Votes)
                
case class PostDTO(@BeanProperty id: String,
                @BeanProperty title: String, 
                @BeanProperty url: String,
                @BeanProperty user: String,
                @BeanProperty src: String )

