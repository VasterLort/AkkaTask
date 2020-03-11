package by.itechart.supervisor.actor

import akka.actor.{ActorLogging, ActorRef, Props}
import akka.pattern.{ask, pipe}
import akka.persistence.{PersistentActor, SnapshotOffer}
import akka.util.Timeout
import by.itechart.action._
import by.itechart.event.{CounterIncrementEvent, CounterResetEvent, Event}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

case class CompanyCounter(count: Int) {
  def update(evt: Event) = evt match {
    case CounterIncrementEvent() => copy(count + 1)
    case CounterResetEvent() => copy(0)
  }
}

class CompanyActor(name: String) extends PersistentActor with ActorLogging {
  implicit val timeout = Timeout(10.seconds)
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
      if (ref.isInstanceOf[ActorRef]){
        log.info("Success")
        Future.successful(SuccessfulMessage()).pipeTo(sender())
      }else{
        log.info("Failed")
        Future.successful(FailureMessage()).pipeTo(sender())
      }
    }
    case message: SendMessageToUser => {
      val userActor = userNameToActor(message.userName)
      val result = (userActor ? message).mapTo[GetCount]
      persist(CounterIncrementEvent())(updateState)
      saveSnapshot(state)
      result.pipeTo(sender())
    }
    case message: PrintUserCount =>
      val userActor = userNameToActor(message.userName)
      val result = userActor ? message
      result.pipeTo(sender())

    case _: PrintCompanyCount =>
      sender ! GetCount(state.count)
  }
}

