package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Company(code: Pk[Long] = NotAssigned
                  , name: String
                  , name_en: String
                  , phone_number: String)

object Company {

  // -- Parsers

  /**
   * Parse a Company from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("company.code") ~
    get[String]("company.name") ~
    get[String]("company.name_en") ~
    get[String]("phone_number") map {
      case code~name~name_en~phone_number => Company(code, name, name_en, phone_number)
    }
  }

  // -- Queries

  /**
   * Retrieve a Company from the code.
   */
  def findById(code: Long): Option[Company] = {
    DB.withConnection { implicit connection =>
      SQL("select * from company where code = {code}").on('code -> code).as(Company.simple.singleOpt)
    }
  }

}

