package by.itechart.action

import akka.actor.ActorRef

sealed trait Message

case class CreateCompany(companyName: String) extends Message

case class CreateUser(companyName: String, userName: String) extends Message

case class SendMessageToCompany(companyName: String) extends Message

case class SendMessageToUser(companyName: String, userName: String) extends Message

case class UpdateCompanyMessage(messageToCompany: SendMessageToCompany, newspaperActor: ActorRef) extends Message

case class UpdateUserMessage(messageToUser: SendMessageToUser, newspaperActor: ActorRef) extends Message

case class CountUserMessages(userName: String, counter: Int) extends Message

case class PrintAmountCompanyMessages(companyName: String, counter: Int) extends Message
