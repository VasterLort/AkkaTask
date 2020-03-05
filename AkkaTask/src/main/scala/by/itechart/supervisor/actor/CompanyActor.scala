package by.itechart.supervisor.actor

import akka.actor.{ActorLogging, ActorRef, Props}
import akka.persistence.{PersistentActor, SnapshotOffer, SnapshotSelectionCriteria}
import by.itechart.action._

sealed trait Evt
case class UpdatedUserMessage() extends Evt

case class ExampleState(count: Int = 0) {
  def increment(): ExampleState = copy(count + 1)

  def clean(): ExampleState = ExampleState(0)
}

class CompanyActor(name: String) extends PersistentActor with ActorLogging {
  private var userNameToActor = Map.empty[String, ActorRef]

  override def persistenceId = name

  var state = ExampleState()

  def incrementState(): Unit =
    state = state.increment()

  def cleanState(): Unit =
    state = state.clean()

  val receiveRecover: Receive = {
    case evt: Evt => incrementState()
    case SnapshotOffer(_, snapshot: ExampleState) => state = snapshot
  }

  val snapShotInterval = 1000
  val receiveCommand: Receive = {
    case message: CreateUser => {
      val ref = context.actorOf(Props[UserActor], name = message.userName)
      userNameToActor += message.userName -> ref
    }
    case message: UpdateUserMessage => {
      val userActor = userNameToActor(message.messageToUser.userName)
      userActor ! message
      persist(UpdatedUserMessage()) { event =>
        incrementState()
        context.system.eventStream.publish(event)
        /*if (lastSequenceNr % snapShotInterval == 0 && lastSequenceNr != 0)
          saveSnapshot(state)*/
        deleteSnapshots(SnapshotSelectionCriteria())
      }
    }
    case message: UpdateCompanyMessage => {
      message.newspaperActor ! PrintAmountCompanyMessages(message.messageToCompany.companyName, state.count)
    }
  }
}

