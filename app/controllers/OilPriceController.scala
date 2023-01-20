package controllers

import models.OilPrice
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.OilPriceService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class OilPriceController @Inject()(val ws: WSClient,
                                   val controllerComponents: ControllerComponents) extends BaseController {

  private val oilPriceService: OilPriceService = new OilPriceService
  loadDataIfMissing()

  // возвращает статистику по данных. Общее число записей
  def getStatistics(): Action[AnyContent] = Action.async {
    val futureStat = oilPriceService.getStats()
    futureStat.map(quantity => Ok(Json.parse("""{"total":""" + quantity.toString + """}""")))
  }

  // возвращает цену по дате
  def getPriceByDate(from: String): Action[AnyContent] = Action.async {
    Future {
      Ok("getPriceByDate()")
    }
  }

  // возвращает среднюю цену по диапазону дат
  def getAveragePriceByRange(from: String, to: String): Action[AnyContent] = Action.async {
    Future {
      Ok("getAveragePriceByRange()")
    }
  }

  // возвращает максимальную и минимальную цену по диапазону дат
  def getMaxAndMinPricesByDateRange(from: String, to: String): Action[AnyContent] = Action.async {
    Future {
      Ok("getMaxAndMinPricesByDateRange()")
    }
  }

  // загружает данные при запуске
  private def loadDataIfMissing(): Unit = {
    oilPriceService.getStats().map(quantity => if (quantity == 0) {
      ws.url("https://data.gov.ru/api/json/dataset/7710349494-urals/version/20230117T155802/content?access_token=d91031dbc18530e8a74e4de3d9759543")
        .get().map(data => {
        val list = Json.fromJson[List[OilPrice]](data.json).get
        oilPriceService.loadPrices(list)
      })
    })
  }
}
