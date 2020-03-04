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
        println(s"New company ${message.companyName} was created!")
        this
      case message: CreateUser =>
        println(s"New user ${message.userName} was created!")
        this
      case message: CountUserMessages =>
        println(s"${message.userName} has eaten  ${message.counter} meals!")
        this
      case message: PrintAmountCompanyMessages =>
        println(s"Company ${message.companyName} users have eaten ${message.counter} meals so far!")
        this
    }
}
