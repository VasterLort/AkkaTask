package by.itechart.supervisor.actor

import akka.actor.{ActorLogging, ActorRef, Props}
import akka.persistence.{PersistentActor, SnapshotOffer}
import by.itechart.action._
import by.itechart.event.{CounterIncrementEvent, CounterResetEvent, Event}

case class CompanyCounter(count: Int) {
  def update(evt: Event) = evt match {
    case CounterIncrementEvent() => copy(count + 1)
    case CounterResetEvent() => copy(0)
  }
}

class CompanyActor(name: String) extends PersistentActor with ActorLogging {
  private var userNameToActor = Map.empty[String, ActorRef]
  var state = CompanyCounter(0)

  def updateState(event: Event) = state = state.update(event)

  override def persistenceId: String = name

  override def receiveRecover: Receive = {
    case evt: Event => updateState(evt)
    case SnapshotOffer(_, snapshot: CompanyCounter) => state = snapshot
  }

  override def receiveCommand: Receive = {
    case message: CreateUser => {
      val ref = context.actorOf(Props(new UserActor(message.userName)), name = message.userName)
      userNameToActor += message.userName -> ref
    }
    case message: UpdateMessageForUser => {
      val userActor = userNameToActor(message.messageToUser.userName)
      userActor ! message
      persist(CounterIncrementEvent())(updateState)
      saveSnapshot(state)
    }
    case message: UpdateUserCounter =>
      val userActor = userNameToActor(message.m.userName)
      userActor ! message

    case message: UpdateCompanyCounter =>
      message.newspaperActor ! CountCompanyMessages(message.m.companyName, state.count)
  }
}

