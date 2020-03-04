package by.itechart.employee.ibm

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object ImbFirstEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new ImbFirstEmployeeActor(_))
}

class ImbFirstEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("ImbFirstEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is ImbFirstEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("ImbFirstEmployeeActor stopped")
      this
  }
}

