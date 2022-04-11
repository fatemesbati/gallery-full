name := """gallery-full"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(
  guice,
  javaJpa,
  "org.postgresql" % "postgresql" % "42.2.4",
  "org.hibernate" % "hibernate-core" % "5.4.9.Final"
)
