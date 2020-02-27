package by.itechart.employee.apple

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object AppleFirstEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new AppleFirstEmployeeActor(_))
}

class AppleFirstEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("AppleFirstEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "name" =>
        println("My name is AppleFirstEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("AppleFirstEmployeeActor stopped")
      this
  }
}
