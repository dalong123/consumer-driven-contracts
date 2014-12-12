import _root_.sbt.Keys._
import _root_.spray.revolver.RevolverPlugin.Revolver

organization := "com.xebia"

name := "provider-scala"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Xlint")

libraryDependencies ++= {
  val akkaVersion  = "2.3.7"
  val sprayVersion = "1.3.2"
  Seq(
    "io.spray"          %% "spray-can"     % sprayVersion,
    "io.spray"          %% "spray-routing" % sprayVersion,
    "io.spray"          %% "spray-json"    % "1.3.1",
    "io.spray"          %% "spray-testkit" % sprayVersion  % Test,
    "com.typesafe.akka" %% "akka-actor"    % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit"  % akkaVersion   % Test,
    "org.specs2"        %% "specs2-core"   % "2.3.11"      % Test
  )
}

Revolver.settings

PactJvmPlugin.pactSettings