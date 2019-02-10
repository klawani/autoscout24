package v1.advert

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CarAdvertHelpers {

  private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

  def formatDate(dateToFormat: String): LocalDate =
    LocalDate.parse(dateToFormat, dateTimeFormatter)

  def createCarAdvertDTO(carAdvertData: CarAdvertData): CarAdvertDTO =
    CarAdvertDTO(
      carAdvertData.id.id,
      carAdvertData.title.title,
      carAdvertData.fuel.toString,
      carAdvertData.price,
      carAdvertData.isNew,
      carAdvertData.mileage,
      carAdvertData.firstRegistration.map(_.toString)
    )

  def createCarAdvertFromDTO(carAdvertDTO: CarAdvertDTO): CarAdvertData =
    CarAdvertData(
      AdvertId(carAdvertDTO.id),
      AdvertTitle(carAdvertDTO.title),
      Fuel(carAdvertDTO.fuel),
      carAdvertDTO.price,
      carAdvertDTO.isNew,
      carAdvertDTO.mileage,
      carAdvertDTO.firstRegistration.map(CarAdvertHelpers.formatDate)
    )
}
