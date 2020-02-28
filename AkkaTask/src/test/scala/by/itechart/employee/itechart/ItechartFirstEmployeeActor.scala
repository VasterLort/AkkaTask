package by.itechart.employee.itechart

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object ItechartFirstEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new ItechartFirstEmployeeActor(_))
}

class ItechartFirstEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("ItechartFirstEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is ItechartFirstEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("ItechartFirstEmployeeActor stopped")
      this
  }
}
