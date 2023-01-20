ThisBuild / scalaVersion := "2.13.10"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """EmbedikaTestTask""",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.40.0.0"
libraryDependencies ++= Seq("org.flywaydb" %% "flyway-play" % "7.25.0")
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.4.1"
libraryDependencies += ws
