val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "PPS-23-Uno",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    Compile / mainClass := Some("Main"),
    Compile / resourceDirectory := baseDirectory.value / "src/main/resources",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "org.playframework" %% "play-json" % "3.0.4"
    ),
    coverageEnabled := true,
    assembly / assemblyJarName := "Uno.jar",
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case _                        => MergeStrategy.first
    }
  )
