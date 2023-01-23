package models

import play.api.libs.json.{Json, Reads, Writes}

case class MinMaxPrice (max: Double, min: Double) extends Serializable{
}

object MinMaxPrice {
  implicit val reads: Reads[MinMaxPrice] = Json.reads[MinMaxPrice]

  implicit val writes: Writes[MinMaxPrice] = Json.writes[MinMaxPrice]

  def apply(max: Double, min: Double): MinMaxPrice = {
    new MinMaxPrice(max, min)
  }
}