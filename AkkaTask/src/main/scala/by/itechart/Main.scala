package by.itechart

import akka.actor.typed.ActorSystem
import by.itechart.supervisor.SupervisingActor

object Main {
  def main(args: Array[String]): Unit = {
    val testSystem = ActorSystem[String](SupervisingActor(), "supervisor-actor")
    testSystem ! "startApple"
    testSystem ! "startIbm"
    testSystem ! "startItechart"
    testSystem ! "stop"
  }
}
