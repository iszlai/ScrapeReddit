package com.scrape.model

import scala.beans.BeanProperty

case class TopPostCategory(@BeanProperty max: Int,
                           @BeanProperty src: String,
                           @BeanProperty title: String,
                           @BeanProperty url: String,
                           @BeanProperty user: String)

case class UserStat(@BeanProperty count: Int,
                    @BeanProperty all: Int,
                    @BeanProperty user: String)

case class CategoryStat(@BeanProperty avgScore: Int,
                        @BeanProperty category: String)                           
                           