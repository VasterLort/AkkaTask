package by.itechart.supervisor.actor

import akka.actor.{Actor, ActorLogging}
import by.itechart.action.{CountUserMessages, UpdateUserMessage}

class UserActor extends Actor with ActorLogging {
  private var count = 0

  def receive = {
    case message: UpdateUserMessage =>
      count += 1
      message.newspaperActor ! CountUserMessages(message.messageToUser.userName, count)
      this
  }
}
