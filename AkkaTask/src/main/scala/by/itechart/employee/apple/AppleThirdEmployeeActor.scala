package by.itechart.employee.apple

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object AppleThirdEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new AppleThirdEmployeeActor(_))
}

class AppleThirdEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("AppleThirdEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is AppleThirdEmployeeActor")
        this
    }
  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("AppleThirdEmployeeActor stopped")
      this
  }
}
