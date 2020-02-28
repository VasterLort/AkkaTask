package by.itechart.employee.apple

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object AppleSecondEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new AppleSecondEmployeeActor(_))
}

class AppleSecondEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("AppleSecondEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is AppleSecondEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("AppleSecondEmployeeActor stopped")
      this
  }
}
