package by.itechart.supervisor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import by.itechart.action._
import by.itechart.supervisor.actor.CompanyActor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

class SupervisorActor extends Actor with ActorLogging {
  implicit val timeout = Timeout(10.seconds)
  private var companyNameToActor = Map.empty[String, ActorRef]

  def receive = {
    case message: CreateCompany =>
      val ref = context.actorOf(Props(new CompanyActor(message.companyName)), name = message.companyName)
      companyNameToActor += message.companyName -> ref
      if (ref.isInstanceOf[ActorRef]){
        log.info("Success")
        Future.successful(SuccessfulMessage()).pipeTo(sender())
      }else{
        log.info("Failed")
        Future.successful(FailureMessage()).pipeTo(sender())
      }
    case message: CreateUser =>
      val companyActor = companyNameToActor(message.companyName)
      companyActor ! message
    case message: SendMessageToUser =>
      val companyActor = companyNameToActor(message.companyName)
      companyActor ! message
    case message: PrintCompanyCount =>
      val companyActor = companyNameToActor(message.companyName)
      val result = companyActor ? message
      result.pipeTo(sender())
    case message: PrintUserCount =>
      val companyActor = companyNameToActor(message.companyName)
      val result = companyActor ? message
      result.pipeTo(sender())
  }
}
