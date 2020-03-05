package by.itechart.supervisor

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior, SupervisorStrategy}
import by.itechart.action._
import by.itechart.supervisor.actor.{CompanyActor, NewspaperActor}

object SupervisorActor {
  def apply(): Behavior[Message] =
    Behaviors.setup(context => new SupervisorActor(context))
}

class SupervisorActor(context: ActorContext[Message]) extends AbstractBehavior[Message](context) {
  private val newspaper = context.spawn(Behaviors.supervise(NewspaperActor()).onFailure(SupervisorStrategy.restart), name = "newspaper")
  private var companyNameToActor = Map.empty[String, ActorRef[Message]]

  override def onMessage(msg: Message): Behavior[Message] =
    msg match {
      case message: CreateCompany =>
        val ref = context.spawn(Behaviors.supervise(CompanyActor()).onFailure(SupervisorStrategy.restart), name = message.companyName)
        newspaper ! message
        companyNameToActor += message.companyName -> ref
        this
      case message: CreateUser =>
        val companyActor = companyNameToActor(message.companyName)
        newspaper ! message
        companyActor ! message
        this
      case message: SendMessageToUser =>
        val companyActor = companyNameToActor(message.companyName)
        companyActor ! UpdateUserMessage(message, newspaper)
        this
      case message: SendMessageToCompany =>
        val companyActor = companyNameToActor(message.companyName)
        companyActor ! UpdateCompanyMessage(message, newspaper)
        this
    }
}
