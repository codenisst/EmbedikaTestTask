package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class InputData(fromDate: String, toDate: String, var price: String) extends Serializable{

}

object InputData {
  implicit val reads: Reads[InputData] = (
    (JsPath \ "Начало периодамониторинга цен на нефть").read[String] and
      (JsPath \ "Конец периодамониторинга цен на нефть").read[String] and
      (JsPath \ "Средняя цена на нефть сырую марки «Юралс» на мировых рынках нефтяного сырья (средиземноморском и роттердамском)").read[String]
  ) (InputData.apply _)

  implicit val writes: Writes[InputData] = Json.writes[InputData]
}
