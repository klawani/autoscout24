package v1.advert

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CarAdvertHelpers {

  private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

  def formatDate(dateToFormat: String): LocalDate =
    LocalDate.parse(dateToFormat, dateTimeFormatter)
}
