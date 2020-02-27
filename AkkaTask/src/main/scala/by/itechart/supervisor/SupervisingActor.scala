package by.itechart.supervisor

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, SupervisorStrategy}
import by.itechart.company.AppleActor

object SupervisingActor {
  def apply(): Behavior[String] =
    Behaviors.setup(context => new SupervisingActor(context))
}

class SupervisingActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  lazy private val child = context.spawn(
    Behaviors.supervise(AppleActor()).onFailure(SupervisorStrategy.restart), name = "apple")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "start" =>
        child ! "secondChild"
        this
    }
}
