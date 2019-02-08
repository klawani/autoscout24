package v1.advert

import java.time.LocalDate

import play.api.libs.json.Json
import v1.advert.Fuel.Gasoline

final case class CarAdvertData(id: AdvertId,
                               title: AdvertTitle,
                               fuel: Fuel = Gasoline,
                               price: Int = 0,
                               isNew: Boolean = true,
                               mileage: Option[Int] = None,
                               firstRegistration: Option[LocalDate] = None)

sealed trait Fuel

object Fuel {

  case object Gasoline extends Fuel
  case object Diesel   extends Fuel

  def apply(name: String): Fuel = name.toLowerCase match {
    case "gasoline" => Gasoline
    case "diesel"   => Diesel
  }
}

class AdvertId private (val id: Int) extends AnyVal with Ordered[AdvertId] {
  override def toString: String = id.toString
  override def compare(that: AdvertId): Int =
    this.id.compare(that.id)
}

object AdvertId {
  def apply(raw: Integer): AdvertId = {
    require(raw != null)
    new AdvertId(raw)
  }
}

class AdvertTitle(val title: String) {}

object AdvertTitle {
  def apply(raw: String): AdvertTitle = {
    require(raw != null)
    new AdvertTitle(raw)
  }
}

case class CarAdvertDTO(id: Int,
                        title: String,
                        fuel: String,
                        price: Int,
                        isNew: Boolean,
                        mileage: Option[Int] = None,
                        firstRegistration: Option[String] = None)

object CarAdvertDTO {

  implicit val carAdvertDTOReads = Json.reads[CarAdvertDTO]
  implicit val carAvertDTOWrites = Json.writes[CarAdvertDTO]
}
