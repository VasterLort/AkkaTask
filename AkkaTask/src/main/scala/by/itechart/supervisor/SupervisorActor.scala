package by.itechart.supervisor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import by.itechart.action._
import by.itechart.supervisor.actor.{CompanyActor, NewspaperActor}

import scala.concurrent.Future
import scala.concurrent.duration._

class SupervisorActor extends Actor with ActorLogging {
  implicit val timeout = Timeout(10.seconds)
  private val newspaper = context.actorOf(Props[NewspaperActor], name = "newspaper")
  private var companyNameToActor = Map.empty[String, ActorRef]

  def receive = {
    case message: CreateCompany =>
      val ref = context.actorOf(Props(new CompanyActor(message.companyName)), name = message.companyName)
      newspaper ! message
      companyNameToActor += message.companyName -> ref
    case message: CreateUser =>
      val companyActor = companyNameToActor(message.companyName)
      newspaper ! message
      companyActor ! message
    case message: SendMessageToUser =>
      val companyActor = companyNameToActor(message.companyName)
      companyActor ! UpdateMessageForUser(message, newspaper)
    case message: PrintCompanyCounter =>
      val companyActor = companyNameToActor(message.companyName)
      val result = (companyActor ? UpdateCompanyCounter(message, newspaper))
      sender ! result
    case message: PrintUserCounter =>
      val companyActor = companyNameToActor(message.companyName)
      (companyActor ? UpdateUserCounter(message, newspaper)).mapTo[String]
  }
}
