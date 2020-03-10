package by.itechart.action

sealed trait Message

case class CreateCompany(companyName: String) extends Message

case class CreateUser(companyName: String, userName: String) extends Message

case class SendMessageToUser(companyName: String, userName: String) extends Message

case class PrintCompanyCount(companyName: String) extends Message

case class PrintUserCount(companyName: String, userName: String) extends Message

case class GetCount(count: Int) extends Message

case class UserName(userName: String) extends Message