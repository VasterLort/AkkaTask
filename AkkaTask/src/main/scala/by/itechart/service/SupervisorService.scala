package by.itechart.service

import akka.actor.ActorRef
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives
import akka.pattern.ask
import akka.util.Timeout
import by.itechart.action._
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.{Operation, Parameter}
import javax.ws.rs.{Consumes, GET, POST, Path}
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val creatingCompanyFormat = jsonFormat1(CreateCompany)
  implicit val creatingUserFormat = jsonFormat2(CreateUser)
  implicit val sendingMessageToUserFormat = jsonFormat2(SendMessageToUser)
  implicit val printingCompanyCounterFormat = jsonFormat1(PrintCompanyCount)
  implicit val printingUserCounterFormat = jsonFormat2(PrintUserCount)
  implicit val userNameFormat = jsonFormat1(UserName)
  implicit val countFormat = jsonFormat1(GetCount)
}

@Path("/supervisor")
class SupervisorService(supervisor: ActorRef)(implicit executionContext: ExecutionContext) extends Directives with JsonSupport {
  implicit val timeout = Timeout(10.seconds)

  val route =
    pathPrefix("supervisor") {
      createCompany ~
        createUser ~
        sendMessageToUser ~
        printCompanyCounter ~
        printUserCounter
    }

  @POST
  @Consumes(Array("application/json"))
  @Path("companies")
  @Operation(
    requestBody = new RequestBody(content = Array(new Content(schema = new Schema(implementation = classOf[CreateCompany]))))
  )
  def createCompany =
    pathPrefix("companies") {
      pathEndOrSingleSlash {
        post {
          entity(as[CreateCompany]) { record =>
            complete {
              supervisor ! CreateCompany(record.companyName)
              HttpResponse(200)
            }
          }
        }
      }
    }

  @POST
  @Consumes(Array("application/json"))
  @Path("companies/{companyName}/users")
  @Operation(
    parameters = Array(
      new Parameter(name = "companyName", in = ParameterIn.PATH, required = true,
        schema = new Schema(implementation = classOf[String]))
    ),
    requestBody = new RequestBody(content = Array(new Content(schema = new Schema(implementation = classOf[UserName]))))
  )
  def createUser =
    pathPrefix("companies" / Segment) { companyName =>
      pathPrefix("users") {
        pathEndOrSingleSlash {
          post {
            entity(as[UserName]) { record =>
              complete {
                supervisor ! CreateUser(companyName, record.userName)
                HttpResponse(200)
              }
            }
          }
        }
      }
    }

  @POST
  @Consumes(Array("application/json"))
  @Path("companies/{companyName}/users/{userName}/messages")
  @Operation(
    parameters = Array(
      new Parameter(name = "companyName", in = ParameterIn.PATH, required = true,
        schema = new Schema(implementation = classOf[String])),
      new Parameter(name = "userName", in = ParameterIn.PATH, required = true,
        schema = new Schema(implementation = classOf[String]))
    ),
  )
  def sendMessageToUser =
    pathPrefix("companies" / Segment) { companyName =>
      pathPrefix("users" / Segment) { userName =>
        pathPrefix("messages") {
          pathEndOrSingleSlash {
            post {
              complete {
                supervisor ! SendMessageToUser(companyName, userName)
                HttpResponse(200)
              }
            }
          }
        }
      }
    }

  @GET
  @Path("companies/{companyName}/messages/count")
  @Operation(
    parameters = Array(
      new Parameter(name = "companyName", in = ParameterIn.PATH, required = true,
        schema = new Schema(implementation = classOf[String]))
    ),
  )
  def printCompanyCounter =
    pathPrefix("companies" / Segment) { companyName =>
      pathPrefix("messages") {
        pathPrefix("count") {
          pathEndOrSingleSlash {
            get {
              val res = (supervisor ? PrintCompanyCount(companyName)).mapTo[GetCount]
              complete(res)
            }
          }
        }
      }
    }

  @GET
  @Consumes(Array("application/json"))
  @Path("companies/{companyName}/users/{userName}/messages/count")
  @Operation(
    parameters = Array(
      new Parameter(name = "companyName", in = ParameterIn.PATH, required = true,
        schema = new Schema(implementation = classOf[String])),
      new Parameter(name = "userName", in = ParameterIn.PATH, required = true,
        schema = new Schema(implementation = classOf[String]))
    ),
  )
  def printUserCounter =
    pathPrefix("companies" / Segment) { companyName =>
      pathPrefix("users" / Segment) { userName =>
        pathPrefix("messages") {
          pathPrefix("count") {
            pathEndOrSingleSlash {
              get {
                val res = (supervisor ? PrintUserCount(companyName, userName)).mapTo[GetCount]
                complete(res)
              }
            }
          }
        }
      }
    }
}
