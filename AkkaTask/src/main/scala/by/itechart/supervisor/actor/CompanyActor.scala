package by.itechart.supervisor.actor

import akka.actor.typed._
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import by.itechart.action._

object CompanyActor {
  def apply(): Behavior[Message] =
    Behaviors.setup(context => new CompanyActor(context))
}

class CompanyActor(context: ActorContext[Message]) extends AbstractBehavior[Message](context) {
  private var userNameToActor = Map.empty[String, ActorRef[Message]]
  private var count = 0

  override def onMessage(msg: Message): Behavior[Message] =
    msg match {
      case message: CreateUser => {
        val ref = context.spawn(Behaviors.supervise(UserActor()).onFailure(SupervisorStrategy.restart), name = message.userName)
        userNameToActor += message.userName -> ref
        this
      }
      case message: UpdateUserMessage => {
        val userActor = userNameToActor(message.messageToUser.userName)
        userActor ! message
        count += 1
        this
      }
      case message: UpdateCompanyMessage => {
        message.newspaperActor ! PrintAmountCompanyMessages(message.messageToCompany.companyName, count)
        this
      }
    }

  override def onSignal: PartialFunction[Signal, Behavior[Message]] = {
    case PreRestart =>
      context.log.info("supervised CompanyActor will be restarted")
      this
    case PostStop =>
      context.log.info("supervised CompanyActor stopped")
      this
  }
}
