package by.itechart.employee.ibm

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object ImbSecondEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new ImbSecondEmployeeActor(_))
}

class ImbSecondEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("ImbSecondEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is ImbSecondEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("ImbSecondEmployeeActor stopped")
      this
  }
}

