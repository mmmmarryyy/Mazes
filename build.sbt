import Dependencies.{Versions => _, _}

lazy val mazes = project
  .settings(
    name := "mazes",
    scalaVersion := Versions.scala3,
    libraryDependencies ++= Seq(scalaTest, scalastic)
  )
