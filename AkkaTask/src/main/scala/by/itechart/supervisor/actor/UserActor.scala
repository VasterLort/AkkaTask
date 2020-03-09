package by.itechart.supervisor.actor

import akka.actor.ActorLogging
import akka.pattern.ask
import akka.persistence.{PersistentActor, SnapshotOffer}
import akka.util.Timeout
import by.itechart.action.{CountUserMessages, UpdateMessageForUser, UpdateUserCounter}
import by.itechart.event.{CounterIncrementEvent, CounterResetEvent, Event}

import scala.concurrent.duration._

case class UserCounter(count: Int) {
  def update(evt: Event) = evt match {
    case CounterIncrementEvent() => copy(count + 1)
    case CounterResetEvent() => copy(0)
  }
}

class UserActor(name: String) extends PersistentActor with ActorLogging {
  implicit val timeout = Timeout(10.seconds)
  var state = UserCounter(0)

  def updateState(event: Event) = state = state.update(event)

  override def persistenceId: String = name

  override def receiveRecover: Receive = {
    case evt: Event => updateState(evt)
    case SnapshotOffer(_, snapshot: UserCounter) => state = snapshot
  }

  override def receiveCommand: Receive = {
    case _: UpdateMessageForUser =>
      persist(CounterIncrementEvent())(updateState)
      saveSnapshot(state)
    case message: UpdateUserCounter =>
      (message.newspaperActor ? CountUserMessages(message.m.userName, state.count)).mapTo[String]
  }
}
