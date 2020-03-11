package by.itechart

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestKit
import by.itechart.action._
import by.itechart.supervisor.SupervisorActor
import org.scalatest.wordspec.AnyWordSpecLike

class MainTest extends TestKit(ActorSystem("system")) with AnyWordSpecLike {
  "Supervising actor" must {
    "system startup" in {
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

      testSystem ! PrintCompanyCount("Apple")
      testSystem ! PrintCompanyCount("Ibm")
      testSystem ! PrintCompanyCount("Epam")

      testSystem ! PrintUserCount("Epam", "Dfdf")
      testSystem ! PrintUserCount("Ibm", "Jin1")
      testSystem ! PrintUserCount("Ibm", "Sdsd")
      testSystem ! PrintUserCount("Apple", "Jin2")

      Thread.sleep(2000)

      TestKit.shutdownActorSystem(system)
    }
  }
}
