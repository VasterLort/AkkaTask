package by.itechart.service

import akka.actor.ActorRef
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives
import akka.pattern.ask
import akka.util.Timeout
import by.itechart.action._
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import javax.ws.rs.{GET, POST, Path}
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val creatingUserFormat = jsonFormat2(CreateUser)
  implicit val sendingMessageToUserFormat = jsonFormat2(SendMessageToUser)
  implicit val creatingCompanyFormat = jsonFormat1(CreateCompany)
  implicit val printingCompanyCounterFormat = jsonFormat1(PrintCompanyCounter)
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
  @Operation(
    requestBody = new RequestBody(content = Array(new Content(schema = new Schema(implementation = classOf[CreateCompany])))),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Hello response",
        content = Array(new Content(schema = new Schema(implementation = classOf[CreateCompany])))),
      new ApiResponse(responseCode = "500", description = "Internal server error"))
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
  @Operation(
    requestBody = new RequestBody(content = Array(new Content(schema = new Schema(implementation = classOf[CreateUser])))),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Hello response",
        content = Array(new Content(schema = new Schema(implementation = classOf[CreateUser])))),
      new ApiResponse(responseCode = "500", description = "Internal server error"))
  )
  def createUser =
    pathPrefix("companies") {
      pathPrefix("company") {
        pathPrefix("users") {
          pathEndOrSingleSlash {
            post {
              entity(as[CreateUser]) { record =>
                complete {
                  supervisor ! CreateUser(record.companyName, record.userName)
                  HttpResponse(200)
                }
              }
            }
          }
        }
      }
    }

  @POST
  @Operation(
    requestBody = new RequestBody(content = Array(new Content(schema = new Schema(implementation = classOf[SendMessageToUser])))),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Hello response",
        content = Array(new Content(schema = new Schema(implementation = classOf[SendMessageToUser])))),
      new ApiResponse(responseCode = "500", description = "Internal server error"))
  )
  def sendMessageToUser =
    pathPrefix("companies") {
      pathPrefix("company") {
        pathPrefix("users") {
          pathPrefix("user") {
            pathPrefix("messages") {
              pathEndOrSingleSlash {
                post {
                  entity(as[SendMessageToUser]) { record =>
                    complete {
                      supervisor ! SendMessageToUser(record.companyName, record.userName)
                      HttpResponse(200)
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

  @GET
  @Operation(summary = "Return Hello greeting", description = "Return Hello greeting for named user",
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Hello response",
        content = Array(new Content(schema = new Schema(implementation = classOf[String])))),
      new ApiResponse(responseCode = "500", description = "Internal server error"))
  )
  def printCompanyCounter =
    pathPrefix("companies") {
      pathPrefix("company") {
        pathPrefix("users") {
          pathPrefix("user") {
            pathPrefix("messages") {
              pathPrefix("number") {
                pathEndOrSingleSlash {
                  get {
                    entity(as[PrintCompanyCounter]) { record =>
                        (supervisor ? PrintCompanyCounter(record.companyName)).mapTo[String].map(response => {
                          complete(HttpEntity(ContentTypes.`application/json`, response))
                        }
                        )
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

  @GET
  @Path("companies/{companyName}/users/{userName}/messages/number")
  def printUserCounter =
    parameters(Symbol("companyName"), Symbol("userName")) { (companyName, userName) =>
      get {
        complete {
          (supervisor ? PrintUserCounter(companyName, userName)).mapTo[String]
        }
      }
    }
}
