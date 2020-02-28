package by.itechart.employee.itechart

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object ItechartFourthEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new ItechartFourthEmployeeActor(_))
}

class ItechartFourthEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("ItechartFourthEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is ItechartFourthEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("ItechartFourthEmployeeActor stopped")
      this
  }
}