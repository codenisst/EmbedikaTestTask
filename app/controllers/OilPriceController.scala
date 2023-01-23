package controllers

import models.{InputData, OilPrice}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.OilPriceService

import java.text.{DateFormatSymbols, ParseException, SimpleDateFormat}
import java.util.Locale
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class OilPriceController @Inject()(val ws: WSClient,
                                   val controllerComponents: ControllerComponents) extends BaseController {

  private val oilPriceService: OilPriceService = new OilPriceService
  private val parserFromRequest = new SimpleDateFormat("dd-MM-yyyy")
  private val formatterFromRequest = new SimpleDateFormat("yyyyMMdd")
  loadDataIfMissing()

  // возвращает цену по дате
  def getPriceByDate(from: String): Action[AnyContent] = Action.async {
    try {
      val dateFromRequest = formatterFromRequest.format(parserFromRequest.parse(from)).toInt

      oilPriceService.getPriceByDate(dateFromRequest).map(price => if (price != 0.0) Ok(price.toString) else NotFound)
    } catch {
      case _: ParseException => Future {
        BadRequest("Invalid request parameters")
      }
    }
  }

  // возвращает среднюю цену по диапазону дат
  def getAveragePriceByRange(from: String, to: String): Action[AnyContent] = Action.async {
    try {
      val fromDate = formatterFromRequest.format(parserFromRequest.parse(from)).toInt
      val toDate = formatterFromRequest.format(parserFromRequest.parse(to)).toInt

      println(toDate)

      oilPriceService.getAvePriceByRange(fromDate, toDate).map(price => Ok(price.toString))
    } catch {
      case _: ParseException => Future {
        BadRequest("Invalid request parameters")
      }
    }
  }

  // возвращает максимальную и минимальную цену по диапазону дат
  def getMaxAndMinPricesByDateRange(from: String, to: String): Action[AnyContent] = Action.async {
    try {
      val fromDate = formatterFromRequest.format(parserFromRequest.parse(from)).toInt
      val toDate = formatterFromRequest.format(parserFromRequest.parse(to)).toInt

      oilPriceService.getMaxAndMinPriceByRange(fromDate, toDate).map(list =>
        Ok(Json.parse("""{"max":""" + list.head + """, "min":""" + list.last + """}""")))
    } catch {
      case _: ParseException => Future {
        BadRequest("Invalid request parameters")
      }
    }
  }

  // возвращает статистику по данных. Общее число записей
  def getStatistics(): Action[AnyContent] = Action.async {
    val futureStat = oilPriceService.getStats()
    futureStat.map(quantity => Ok(Json.parse("""{"total":""" + quantity.toString + """}""")))
  }

  // загружает данные при запуске
  private def loadDataIfMissing(): Unit = {
    oilPriceService.getStats().map(quantity =>
      if (quantity == 0) {
        val locale: Locale = new Locale("ru", "RU")
        val dfs: DateFormatSymbols = DateFormatSymbols.getInstance(locale)
        val arrayString: Array[String] =
          Array("янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек")
        dfs.setShortMonths(arrayString)
        val parserFromInputData: SimpleDateFormat = new SimpleDateFormat("dd.MMM.yy")
        parserFromInputData.setDateFormatSymbols(dfs)
        val formatterFromInputData: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")

        ws.url("https://data.gov.ru/api/json/dataset/7710349494-urals/version/20230117T155802/content?access_token=d91031dbc18530e8a74e4de3d9759543")
          .get().map(data => {
          val list = Json.fromJson[List[InputData]](data.json).get.map(inputData => {
            new OilPrice(
              formatterFromInputData.format(parserFromInputData.parse(inputData.fromDate)).toInt,
              formatterFromInputData.format(parserFromInputData.parse(inputData.toDate)).toInt,
              inputData.price.replace(",", ".").toDouble
            )
          })

          oilPriceService.loadPrices(list)
        })
      })
  }
}
