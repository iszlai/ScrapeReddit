package com.scrape.model

import scala.beans.BeanProperty
import java.sql.Date

case class Votes(@BeanProperty up: Int,
                 @BeanProperty down: Int,
                 @BeanProperty score: Int,
                 @BeanProperty sqltime:String)
                 