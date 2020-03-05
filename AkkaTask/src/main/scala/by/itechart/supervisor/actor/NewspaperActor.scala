package by.itechart.supervisor.actor

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import by.itechart.action._

object NewspaperActor {
  def apply(): Behavior[Message] =
    Behaviors.setup(context => new NewspaperActor(context))
}

class NewspaperActor(context: ActorContext[Message]) extends AbstractBehavior[Message](context) {
  override def onMessage(msg: Message): Behavior[Message] =
    msg match {
      case message: CreateCompany =>
        context.log.info(s"New company ${message.companyName} was created!")
        this
      case message: CreateUser =>
        context.log.info(s"New user ${message.userName} was created!")
        this
      case message: CountUserMessages =>
        context.log.info(s"${message.userName} has eaten  ${message.counter} meals!")
        this
      case message: PrintAmountCompanyMessages =>
        context.log.info(s"Company ${message.companyName} users have eaten ${message.counter} meals so far!")
        this
    }
}
