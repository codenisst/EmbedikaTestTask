package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class OilPrice(fromDate: String, toDate: String, price: String) extends Serializable{

}

object OilPrice {
  implicit val reads: Reads[OilPrice] = (
    (JsPath \ "Начало периодамониторинга цен на нефть").read[String] and
      (JsPath \ "Конец периодамониторинга цен на нефть").read[String] and
      (JsPath \ "Средняя цена на нефть сырую марки «Юралс» на мировых рынках нефтяного сырья (средиземноморском и роттердамском)").read[String]
  ) (OilPrice.apply _)

  implicit val writes: Writes[OilPrice] = Json.writes[OilPrice]
}
