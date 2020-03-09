package by.itechart.action

import akka.actor.ActorRef

sealed trait Message

case class CreateCompany(companyName: String) extends Message

case class CreateUser(companyName: String, userName: String) extends Message

case class SendMessageToUser(companyName: String, userName: String) extends Message

case class UpdateMessageForUser(messageToUser: SendMessageToUser, newspaperActor: ActorRef) extends Message

case class CountUserMessages(userName: String, counter: Int) extends Message

case class CountCompanyMessages(companyName: String, counter: Int) extends Message

case class PrintCompanyCounter(companyName: String) extends Message

case class PrintUserCounter(companyName: String, userName: String) extends Message

case class UpdateCompanyCounter(m: PrintCompanyCounter, newspaperActor: ActorRef) extends Message

case class UpdateUserCounter(m: PrintUserCounter, newspaperActor: ActorRef) extends Message

case class GetCount(count: Int) extends Message