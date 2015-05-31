name := "hello"

version := "1.0"

scalaVersion := "2.11.6"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/" 

libraryDependencies ++={
  Seq(
  "net.sourceforge.htmlunit" % "htmlunit" % "2.16"  withSources() withJavadoc(),
  "org.scala-lang" % "scala-xml" % "2.11.0-M4" withSources() withJavadoc(),
  "com.typesafe.akka" %% "akka-actor" % "2.4-SNAPSHOT",
  "org.xerial" % "sqlite-jdbc" % "3.7.2",
  "de.l3s.boilerpipe" % "boilerpipe" % "1.1.0" withSources()
  )
}
