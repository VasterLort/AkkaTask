package by.itechart.company

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, PreRestart, Signal}
import by.itechart.employee.apple.{AppleFirstEmployeeActor, AppleFourthEmployeeActor, AppleSecondEmployeeActor, AppleThirdEmployeeActor}

object AppleActor {
  def apply(): Behavior[String] =
    Behaviors.setup(context => new AppleActor(context))
}

class AppleActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("AppleActor started")
  lazy private val child1 = context.spawn(AppleFirstEmployeeActor(), "first")
  lazy private val child2 = context.spawn(AppleSecondEmployeeActor(), "second")
  lazy private val child3 = context.spawn(AppleThirdEmployeeActor(), "third")
  lazy private val child4 = context.spawn(AppleFourthEmployeeActor(), "fourth")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "firstChild" => {
        child1 ! "printName"
        this
      }
      case "secondChild" => {
        child2 ! "printName"
        this
      }
      case "thirdChild" => {
        child3 ! "printName"
        this
      }
      case "fourthChild" => {
        child4 ! "printName"
        this
      }
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PreRestart =>
      println("supervised AppleActor will be restarted")
      this
    case PostStop =>
      println("supervised AppleActor stopped")
      this
  }
}
