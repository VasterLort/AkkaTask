package by.itechart.employee.itechart

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object ItechartSecondEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new ItechartSecondEmployeeActor(_))
}

class ItechartSecondEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("ItechartSecondEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is ItechartSecondEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("ItechartSecondEmployeeActor stopped")
      this
  }
}

