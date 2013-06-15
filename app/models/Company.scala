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

  /**
   * Return a page of (Company).
   *
   * @param page Page to display
   * @param pageSize Number of computers per page
   * @param orderBy Company property used for sorting
   * @param filter Filter applied on the name column
   */
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[(Company)] = {

    val offest = pageSize * page

    DB.withConnection { implicit connection =>

      val companies = SQL(
        """
          select *
          from company
          where company.name like {filter}
          order by {orderBy} nulls last
          limit {pageSize} offset {offset}
        """
      ).on(
        'pageSize -> pageSize
        , 'offset -> offest
        , 'filter -> filter
        , 'orderBy -> orderBy
      ).as(Company.simple *)

      val totalRows = SQL(
        """
          select count(*)
          from company
          where company.name like {filter}
        """
      ).on(
        'filter -> filter
      ).as(scalar[Long].single)

      Page(companies, page, offest, totalRows)

    }
  }

}

