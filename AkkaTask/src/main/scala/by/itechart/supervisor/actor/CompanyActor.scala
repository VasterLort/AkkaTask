package by.itechart.supervisor.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import by.itechart.action._

class CompanyActor extends Actor with ActorLogging {
  private var userNameToActor = Map.empty[String, ActorRef]
  private var count = 0

  def receive = {
    case message: CreateUser => {
      val ref = context.actorOf(Props[UserActor], name = message.userName)
      userNameToActor += message.userName -> ref
    }
    case message: UpdateUserMessage => {
      val userActor = userNameToActor(message.messageToUser.userName)
      userActor ! message
      count += 1
    }
    case message: UpdateCompanyMessage => {
      message.newspaperActor ! PrintAmountCompanyMessages(message.messageToCompany.companyName, count)
    }
  }
}

