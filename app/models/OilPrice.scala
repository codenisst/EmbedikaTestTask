package models

import play.api.libs.json.{Json, Reads, Writes}

case class OilPrice(fromDate: Int, toDate: Int, price: Double) extends Serializable{

}

object OilPrice {
  implicit val reads: Reads[OilPrice] = Json.reads[OilPrice]

  implicit val writes: Writes[OilPrice] = Json.writes[OilPrice]
}
