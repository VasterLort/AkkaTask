package by.itechart.supervisor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import by.itechart.action._
import by.itechart.supervisor.actor.{CompanyActor, NewspaperActor}

class SupervisorActor extends Actor with ActorLogging {
  private val newspaper = context.actorOf(Props[NewspaperActor], name = "newspaper")
  private var companyNameToActor = Map.empty[String, ActorRef]

  def receive = {
    case message: CreateCompany =>
      val ref = context.actorOf(Props[CompanyActor], name = message.companyName)
      newspaper ! message
      companyNameToActor += message.companyName -> ref
    case message: CreateUser =>
      val companyActor = companyNameToActor(message.companyName)
      newspaper ! message
      companyActor ! message
    case message: SendMessageToUser =>
      val companyActor = companyNameToActor(message.companyName)
      companyActor ! UpdateUserMessage(message, newspaper)
    case message: SendMessageToCompany =>
      val companyActor = companyNameToActor(message.companyName)
      companyActor ! UpdateCompanyMessage(message, newspaper)
  }
}
