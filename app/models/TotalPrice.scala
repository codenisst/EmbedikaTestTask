package models

import play.api.libs.json.{Json, Reads, Writes}

case class TotalPrice(total: Int) extends Serializable{

}

object TotalPrice{
  implicit val reads: Reads[TotalPrice] = Json.reads[TotalPrice]

  implicit val writes: Writes[TotalPrice] = Json.writes[TotalPrice]

  def apply(total: Int): TotalPrice = {
    new TotalPrice(total)
  }
}
