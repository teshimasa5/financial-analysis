package controllers

import play.api._
import play.api.mvc._
import views._

object Stocks extends Controller {

  def index = Action {
    Ok(html.stocks.index());
  }

  def history = Action {
    Ok(html.stocks.history());
  }
}