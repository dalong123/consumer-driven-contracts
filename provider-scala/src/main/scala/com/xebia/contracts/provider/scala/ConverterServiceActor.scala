package com.xebia.contracts.provider.scala

import akka.actor.{Actor, Props}
import spray.http.MediaTypes._
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService

object ConverterServiceActor {
  def props = Props[ConverterServiceActor]
  def name = "converter-service"
}

case class State(state: String)

object StateJsonSupport extends DefaultJsonProtocol {
  implicit val stateFormat = jsonFormat1(State)
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
  import SprayJsonSupport._
  import StateJsonSupport._

  private var state: State = new State("initial state")

  val myRoute = path("convert" / "1.00" / "EUR" / "to" / "USD") {
    get {
      respondWithMediaType(`application/json`) {
        complete {
          println(s"Handling request, state $state")
          """{"amount": 1.24}"""
        }
      }
    }
  } ~
  path("enterState") {
    post {
      entity(as[State]) { state =>
        println(s"Received state: $state")
        this.state = state
        complete("OK")
      } ~
      entity(as[String]) { state =>
        println(s"Received state: $state")
        complete(state)
      }
    }
  }
}
