package by.itechart.employee.itechart

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object ItechartThirdEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new ItechartThirdEmployeeActor(_))
}

class ItechartThirdEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("ItechartThirdEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is ItechartThirdEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("ItechartThirdEmployeeActor stopped")
      this
  }
}

