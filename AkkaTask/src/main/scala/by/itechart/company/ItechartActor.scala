package by.itechart.company

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{Behavior, PostStop, PreRestart, Signal}
import by.itechart.employee.itechart.{ItechartFirstEmployeeActor, ItechartFourthEmployeeActor, ItechartSecondEmployeeActor, ItechartThirdEmployeeActor}

object ItechartActor {
  def apply(): Behavior[String] =
    Behaviors.setup(context => new ItechartActor(context))
}

class ItechartActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("ItechartActor started")
  lazy private val child1 = context.spawn(ItechartFirstEmployeeActor(), "first")
  lazy private val child2 = context.spawn(ItechartSecondEmployeeActor(), "second")
  lazy private val child3 = context.spawn(ItechartThirdEmployeeActor(), "third")
  lazy private val child4 = context.spawn(ItechartFourthEmployeeActor(), "fourth")

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
      println("supervised ItechartActor will be restarted")
      this
    case PostStop =>
      println("supervised ItechartActor stopped")
      this
  }
}

