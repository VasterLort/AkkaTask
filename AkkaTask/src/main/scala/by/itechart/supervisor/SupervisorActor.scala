package by.itechart.supervisor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import by.itechart.action._
import by.itechart.supervisor.actor.{CompanyActor, NewspaperActor}

class SupervisorActor extends Actor with ActorLogging {
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
      companyActor ! UpdateCompanyCounter(message, newspaper)
    case message: PrintUserCounter =>
      val companyActor = companyNameToActor(message.companyName)
      companyActor ! UpdateUserCounter(message, newspaper)
  }
}
