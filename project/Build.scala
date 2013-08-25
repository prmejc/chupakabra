import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "chupakabra"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "commons-io" % "commons-io" % "2.3",
    "com.typesafe" 					        %% "play-plugins-mailer" 	    % "2.1.0",
    javaCore,
    javaJdbc,
    javaEbean,
    anorm
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
