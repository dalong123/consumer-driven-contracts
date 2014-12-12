package com.xebia.contracts.provider.scala

import akka.actor.ActorSystem
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http

import scala.concurrent.duration._

object Main extends App {
  implicit val system = ActorSystem("moneymon")

  val service = system.actorOf(ConverterServiceActor.props, ConverterServiceActor.name)

  implicit val timeout = Timeout(5.seconds)

  IO(Http) ? Http.Bind(service, interface = "localhost", port = 9000)
}
