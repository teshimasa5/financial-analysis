package controllers

import play.api._
import play.api.mvc._
import views._
import models._

object Stocks extends Controller {

  def index = Action{
    list(0, 2, "");
  }

  def detail = Action {
    Ok(html.stocks.detail());
  }

  def history = Action {
    Ok(html.stocks.history());
  }

  def list(page: Int, orderBy: Int, filter: String) = Action { implicit request =>
    Ok(html.stocks.list(
      Company.list(
          page = page
          , orderBy = orderBy
          , filter = ("%"+filter+"%")
      ) ,orderBy, filter
    ))
  }
}