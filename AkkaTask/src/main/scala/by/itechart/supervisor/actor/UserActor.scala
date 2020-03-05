package by.itechart.supervisor.actor

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}
import by.itechart.action.{CountUserMessages, Message, UpdateUserMessage}

object UserActor {
  def apply(): Behavior[Message] =
    Behaviors.setup(new UserActor(_))
}

class UserActor(context: ActorContext[Message]) extends AbstractBehavior[Message](context) {
  private var count = 0

  override def onMessage(msg: Message): Behavior[Message] =
    msg match {
      case message: UpdateUserMessage =>
        count += 1
        message.newspaperActor ! CountUserMessages(message.messageToUser.userName, count)
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[Message]] = {
    case PostStop =>
      context.log.info("UserActor stopped")
      this
  }
}
