package by.itechart.company

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, PreRestart, Signal}
import by.itechart.employee.ibm.{ImbFirstEmployeeActor, ImbFourthEmployeeActor, ImbSecondEmployeeActor, ImbThirdEmployeeActor}

object IbmActor {
  def apply(): Behavior[String] =
    Behaviors.setup(context => new IbmActor(context))
}

class IbmActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("IbmActor started")
  lazy private val child1 = context.spawn(ImbFirstEmployeeActor(), "first")
  lazy private val child2 = context.spawn(ImbSecondEmployeeActor(), "second")
  lazy private val child3 = context.spawn(ImbThirdEmployeeActor(), "third")
  lazy private val child4 = context.spawn(ImbFourthEmployeeActor(), "fourth")

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
      println("supervised IbmActor will be restarted")
      this
    case PostStop =>
      println("supervised IbmActor stopped")
      this
  }
}
