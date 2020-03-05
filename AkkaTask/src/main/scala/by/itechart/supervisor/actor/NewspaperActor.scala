package by.itechart.supervisor.actor

import akka.actor.{Actor, ActorLogging}
import by.itechart.action._

class NewspaperActor extends Actor with ActorLogging {
  def receive = {
    case message: CreateCompany =>
      log.info(s"New Company ${message.companyName} was created!")
      this
    case message: CreateUser =>
      log.info(s"New user ${message.userName} was created!")
      this
    case message: CountUserMessages =>
      log.info(s"${message.userName} has eaten  ${message.counter} meals!")
      this
    case message: PrintAmountCompanyMessages =>
      log.info(s"Company ${message.companyName} users have eaten ${message.counter} meals so far!")
      this
  }
}
