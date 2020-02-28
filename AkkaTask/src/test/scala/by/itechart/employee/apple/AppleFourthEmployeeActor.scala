package by.itechart.employee.apple

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, Signal}

object AppleFourthEmployeeActor {
  def apply(): Behavior[String] =
    Behaviors.setup(new AppleFourthEmployeeActor(_))
}

class AppleFourthEmployeeActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("AppleFourthEmployeeActor started")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "printName" =>
        println("My name is AppleFourthEmployeeActor")
        this
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("AppleFourthEmployeeActor stopped")
      this
  }
}

