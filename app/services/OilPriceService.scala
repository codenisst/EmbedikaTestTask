package services

import dao.OilPriceDao
import models.{MinMaxPrice, OilPrice, TotalPrice}

import javax.inject.Singleton
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.math.BigDecimal.RoundingMode

@Singleton
class OilPriceService(private val oilPriceDao: OilPriceDao = new OilPriceDao) {

  def getStats(): Future[TotalPrice] = {
    oilPriceDao.getStatsFromDb().map(total => TotalPrice(total))
  }

  def loadPrices(dataList: List[OilPrice]): Unit = {
    oilPriceDao.loadData(dataList)
  }

  def getPriceByDate(date: Int): Future[Double] = {
    oilPriceDao.getPriceByDate(date).map(seq => {
      if (seq.nonEmpty) seq.head else 0.0
    })
  }

  def getAvePriceByRange(fromDate: Int, toDate: Int): Future[Double] = {
    oilPriceDao.getPricesByRange(fromDate, toDate).map(seq => {
      var result: BigDecimal = 0.0
      if (seq.nonEmpty) {
        seq.foreach(double => result = result + double)
        (result / seq.length).setScale(1, RoundingMode.HALF_UP).toDouble
      } else {
        result.toDouble
      }
    })
  }

  def getMaxAndMinPriceByRange(fromDate: Int, toDate: Int): Future[MinMaxPrice] = {
    oilPriceDao.getPricesByRange(fromDate, toDate).map(seq => {
      if (seq.nonEmpty) {
        MinMaxPrice(seq.max, seq.min)
      } else {
        MinMaxPrice(0.0, 0.0)
      }
    })
  }
}
