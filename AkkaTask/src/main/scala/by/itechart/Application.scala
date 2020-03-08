package by.itechart

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.RouteConcatenation
import by.itechart.supervisor.SupervisorActor

object Application extends App with RouteConcatenation {
  implicit val system = ActorSystem("akka-http-system")
  sys.addShutdownHook(system.terminate())

  implicit val executionContext = system.dispatcher

  val supervisor = system.actorOf(Props[SupervisorActor])
  /*  val routes =
      cors() (new SupervisorActor().route ~
        new AddOptionService(addOption).route ~
        new HelloService(hello).route ~
        EchoEnumService.route ~
        SwaggerDocService.routes)*/
  //Http().bindAndHandle(routes, "0.0.0.0", 8080)
}