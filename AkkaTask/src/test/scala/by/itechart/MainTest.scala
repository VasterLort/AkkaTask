package by.itechart

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.{ActorSystem, Props}
import by.itechart.action._
import by.itechart.supervisor.SupervisorActor
import org.scalatest.wordspec.AnyWordSpecLike

class MainTest extends ScalaTestWithActorTestKit with AnyWordSpecLike {
  "Supervising actor" must {
    "system startup" in {
      val system = ActorSystem("system")
      val testSystem = system.actorOf(Props[SupervisorActor], "supervisor-actor")
      testSystem ! CreateCompany("Apple")
      testSystem ! CreateCompany("Ibm")
      testSystem ! CreateCompany("Epam")

      testSystem ! CreateUser("Ibm", "Jin1")
      testSystem ! CreateUser("Ibm", "Sdsd")
      testSystem ! CreateUser("Apple", "Jin2")
      testSystem ! CreateUser("Epam", "Dfdf")

      testSystem ! SendMessageToUser("Epam", "Dfdf")
      testSystem ! SendMessageToUser("Epam", "Dfdf")
      testSystem ! SendMessageToUser("Epam", "Dfdf")
      testSystem ! SendMessageToUser("Epam", "Dfdf")
      testSystem ! SendMessageToUser("Epam", "Dfdf")
      testSystem ! SendMessageToUser("Ibm", "Jin1")
      testSystem ! SendMessageToUser("Ibm", "Jin1")
      testSystem ! SendMessageToUser("Ibm", "Sdsd")
      testSystem ! SendMessageToUser("Ibm", "Sdsd")
      testSystem ! SendMessageToUser("Ibm", "Sdsd")
      testSystem ! SendMessageToUser("Epam", "Dfdf")
      testSystem ! SendMessageToUser("Epam", "Dfdf")
      testSystem ! SendMessageToUser("Apple", "Jin2")
      testSystem ! SendMessageToUser("Apple", "Jin2")
      testSystem ! SendMessageToUser("Apple", "Jin2")
      testSystem ! SendMessageToUser("Apple", "Jin2")
      testSystem ! SendMessageToUser("Ibm", "Jin1")

      testSystem ! SendMessageToCompany("Apple")
      testSystem ! SendMessageToCompany("Ibm")
      testSystem ! SendMessageToCompany("Epam")
    }
  }
}
