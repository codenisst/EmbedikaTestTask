package dao

import models.OilPrice
import models.repositories.OilPriceRepo
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

class OilPriceDao {

  private val db = Database.forURL("jdbc:sqlite:pricedb.sqlite")
  private val oilPriceTable = TableQuery[OilPriceRepo]

  def getStatsFromDb(): Future[Int] = {
    db.run(oilPriceTable.length.result)
  }

  def loadData(list: List[OilPrice]): Unit = {
    val query = oilPriceTable ++= list
    db.run(query)
  }
}
