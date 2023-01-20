package services

import dao.OilPriceDao
import models.OilPrice

import javax.inject.Singleton
import scala.concurrent.Future

@Singleton
class OilPriceService(private val oilPriceDao: OilPriceDao = new OilPriceDao) {

  def getStats(): Future[Int] = {
    oilPriceDao.getStatsFromDb()
  }

  def loadPrices(dataList: List[OilPrice]): Unit = {
    oilPriceDao.loadData(dataList)
  }
}
