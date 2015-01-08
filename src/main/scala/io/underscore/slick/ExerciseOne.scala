package io.underscore.slick

import scala.slick.driver.PostgresDriver.simple._

object ExerciseOne extends App {

  final case class Message(from: String, content: String, id: Long = 0L)

  final class MessageTable(tag: Tag) extends Table[Message](tag, "message") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def from = column[String]("from")
    def content = column[String]("content")
    def * = (from, content, id) <> (Message.tupled, Message.unapply)
  }

  lazy val messages = TableQuery[MessageTable]

  Database.forURL("jdbc:postgresql:essential-slick", user = "essential", password = "trustno1", driver = "org.postgresql.Driver") withSession {
    implicit session ⇒

      //Create Schema 
      messages.ddl.create

      //Define a query 
      val query = for {
        message ← messages
        if message.from === "HAL"
      } yield message

      //Execute a query.
      val messages_from_HAL: List[Message] = query.list

      println(s" ${messages_from_HAL}")
  }

}