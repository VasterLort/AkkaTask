package by.itechart.employee.ibm

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object ImbFourthEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new ImbFourthEmployeeActor(_))
}

class ImbFourthEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("ImbFourthEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is ImbFourthEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("ImbFourthEmployeeActor stopped")
      this
  }
}
