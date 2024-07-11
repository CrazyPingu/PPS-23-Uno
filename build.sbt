val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "PPS-23-Uno",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

//    libraryDependencies += "com.github.sbt" % "junit-interface" % "0.13.2" % Test
    
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "org.playframework" %% "play-json" % "3.0.4"
    )
  )
