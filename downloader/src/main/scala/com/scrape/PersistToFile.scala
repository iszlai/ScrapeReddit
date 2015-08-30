package com.scrape

import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.Paths
import java.io.File

/**
 * @author leheli
 */
object PersistToFile {

  val base:String="/home/leheli/maci/"
  
  def persistTofile(path: String, data: String) = {
    val file = new File(path);
    val directory = new File(file.getParentFile().getAbsolutePath());
    directory.mkdirs();
    val pw = new java.io.PrintWriter(file)
    try pw.write(data) finally pw.close()
  }
  
  
  def getPathFromKey(key:String):String={
     val parts=Array(key.charAt(0),key.charAt(1),key.charAt(2),key.substring(3))
     parts.mkString("/")
  }
  
  def persist(hash:String,data:String){
    val path=base+getPathFromKey(hash)
    persistTofile(path, data)
  }

}