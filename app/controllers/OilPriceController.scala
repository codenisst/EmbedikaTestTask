package controllers

import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class OilPriceController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def getStatistics(): Action[AnyContent] = Action.async {
    Future {
      Ok("getStatistics()")
    }
  }

  def updateDatabase(): Action[AnyContent] = Action.async {
    Future {
      Ok("updateDatabase()")
    }
  }

  def getPriceByDate(from: String): Action[AnyContent] = Action.async {
    Future {
      Ok("getPriceByDate()")
    }
  }

  def getAveragePriceByRange(from: String, to: String): Action[AnyContent] = Action.async {
    Future {
      Ok("getAveragePriceByRange()")
    }
  }

  def getMaxAndMinPricesByDateRange(from: String, to: String): Action[AnyContent] = Action.async {
    Future {
      Ok("getMaxAndMinPricesByDateRange()")
    }
  }
}
