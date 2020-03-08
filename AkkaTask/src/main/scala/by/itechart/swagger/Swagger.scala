package by.itechart.swagger

import com.github.swagger.akka.SwaggerHttpService

object Swagger extends SwaggerHttpService {
  /*override val apiClasses = Set(classOf[AddService], classOf[AddOptionService], classOf[HelloService], EchoEnumService.getClass)
  override val host = "localhost:8080"
  override val info = Info(version = "1.0")
  override val externalDocs = Some(new ExternalDocumentation().description("Core Docs").url("http://acme.com/docs"))
  //override val securitySchemeDefinitions = Map("basicAuth" -> new BasicAuthDefinition())
  override val unwantedDefinitions = Seq("Function1", "Function1RequestContextFutureRouteResult")*/
  override def apiClasses: Set[Class[_]] = ???
}