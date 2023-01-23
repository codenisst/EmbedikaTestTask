package models.repositories

import models.OilPrice
import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.SQLiteProfile.api._

class OilPriceRepo(tag: Tag) extends Table[OilPrice](tag, "OilPrice"){

  def fromDate: Rep[Int] = column[Int]("FromDate")

  def toDate: Rep[Int] = column[Int]("ToDate")

  def price: Rep[Double] = column[Double]("OilPrice")

  override def * : ProvenShape[OilPrice] = (fromDate, toDate, price).mapTo[OilPrice]
}
