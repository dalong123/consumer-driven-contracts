package com.xebia.contracts.provider.scala

import akka.actor.{Props, Actor}
import spray.routing.HttpService
import spray.http.MediaTypes._

object ConverterServiceActor {
  def props = Props[ConverterServiceActor]
  def name = "converter-service"
}

class ConverterServiceActor extends Actor with ConverterService {
  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive: Receive = runRoute(myRoute)
}

trait ConverterService extends HttpService {
  val myRoute = path("convert" / "1.00" / "EUR" / "to" / "USD") {
    get {
      respondWithMediaType(`application/json`) {
        complete {
          """{"amount": 1.24}"""
        }
      }
    }
  }
}