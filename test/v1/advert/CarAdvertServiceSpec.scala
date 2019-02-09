package v1.advert

import org.scalatest.mockito.MockitoSugar
import org.scalatest.AsyncFlatSpec
import org.mockito.Mockito._

import scala.concurrent.Future

class CarAdvertServiceSpec extends AsyncFlatSpec with MockitoSugar {

  private val createCarAdvertDTO = (carAdvertData: CarAdvertData) => {
    val firstRegistration = carAdvertData.firstRegistration.map(_.toString)
    CarAdvertDTO(
      carAdvertData.id.id,
      carAdvertData.title.title,
      carAdvertData.fuel.toString,
      carAdvertData.price,
      carAdvertData.isNew,
      carAdvertData.mileage,
      firstRegistration
    )
  }

  private val carAdverts = Seq(
    CarAdvertData(AdvertId(1),
                  AdvertTitle("BMW X3"),
                  Fuel.Diesel,
                  2000,
                  isNew = false,
                  Some(50000),
                  Some(CarAdvertHelpers.formatDate("2009-03-07"))),
    CarAdvertData(AdvertId(2), AdvertTitle("Toyota Auris")),
    CarAdvertData(AdvertId(3), AdvertTitle("Audi A4 Avant"))
  )

  private val carAdvertRepository = mock[CarAdvertRepository]
  private val carAdvertService    = new CarAdvertService(carAdvertRepository)

  "CarAdvertService" should "return the list of all car adverts" in {
    when(carAdvertRepository.listAdverts(sortBy = None)) thenReturn Future(carAdverts)
    val expectedCarAds                          = carAdverts.map(createCarAdvertDTO)
    val actualCarAds: Future[Seq[CarAdvertDTO]] = carAdvertService.listAdverts(sortBy = None)
    actualCarAds map { actualResult =>
      assert(actualResult == expectedCarAds)
      assert(actualResult.head == expectedCarAds.head)
    }
  }

  "CarAdvertService" should "return the list of all car advert ordered By Title" in {
    val sortedCarsByTitle = carAdverts.sorted(CarAdvertData.orderingByTitle)
    when(carAdvertRepository.listAdverts(sortBy = Option("title"))) thenReturn Future(
      sortedCarsByTitle
    )
    val expectedCarAds = sortedCarsByTitle.map(createCarAdvertDTO)
    val actualCarAds: Future[Seq[CarAdvertDTO]] =
      carAdvertService.listAdverts(sortBy = Option("title"))
    actualCarAds map { actualResult =>
      assert(actualResult == expectedCarAds)
      assert(
        actualResult.head == createCarAdvertDTO(
          CarAdvertData(AdvertId(3), AdvertTitle("Audi A4 Avant"))
        )
      )
    }
  }
}
