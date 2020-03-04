package by.itechart

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.ActorSystem
import by.itechart.supervisor.SupervisingActor
import org.scalatest.wordspec.AnyWordSpecLike

class MainTest extends ScalaTestWithActorTestKit with AnyWordSpecLike {
  "Supervising actor" must {
    "system startup" in {
      val testSystem = ActorSystem[String](SupervisingActor(), "supervisor-actor")
      testSystem ! "appleStart"
      testSystem ! "ibmStart"
      testSystem ! "itechartStart"
      testSystem ! "stop"
    }
  }
}
