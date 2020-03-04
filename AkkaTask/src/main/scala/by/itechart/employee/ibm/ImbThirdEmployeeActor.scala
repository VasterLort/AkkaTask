package by.itechart.employee.ibm

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object ImbThirdEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new ImbThirdEmployeeActor(_))
}

class ImbThirdEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("ImbThirdEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is ImbThirdEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("ImbThirdEmployeeActor stopped")
      this
  }
}
