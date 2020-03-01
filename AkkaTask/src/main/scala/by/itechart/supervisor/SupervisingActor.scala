package by.itechart.supervisor

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, SupervisorStrategy}
import by.itechart.company.{AppleActor, IbmActor, ItechartActor}

object SupervisingActor {
  def apply(): Behavior[String] =
    Behaviors.setup(context => new SupervisingActor(context))
}

class SupervisingActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  lazy private val child1 = context.spawn(
    Behaviors.supervise(AppleActor()).onFailure(SupervisorStrategy.restart), name = "apple")
  lazy private val child2 = context.spawn(
    Behaviors.supervise(IbmActor()).onFailure(SupervisorStrategy.restart), name = "ibm")
  lazy private val child3 = context.spawn(
    Behaviors.supervise(ItechartActor()).onFailure(SupervisorStrategy.restart), name = "itechart")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "appleStart" =>
        child1 ! "secondChild"
        this
      case "ibmStart" =>
        child2 ! "firstChild"
        this
      case "itechartStart" =>
        child3 ! "thirdChild"
        this
      case "stop" => Behaviors.stopped
    }
}
