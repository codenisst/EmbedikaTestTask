package models.repositories

import models.OilPrice
import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.SQLiteProfile.api._

class OilPriceRepo(tag: Tag) extends Table[OilPrice](tag, "OilPrice"){

  def fromDate: Rep[String] = column[String]("FromDate")

  def toDate: Rep[String] = column[String]("ToDate")

  def price: Rep[String] = column[String]("OilPrice")

  override def * : ProvenShape[OilPrice] = (fromDate, toDate, price).mapTo[OilPrice]
}
