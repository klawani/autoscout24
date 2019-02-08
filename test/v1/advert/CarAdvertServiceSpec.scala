package v1.advert

import org.scalatest.AsyncFlatSpec
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._

import scala.concurrent.Future

class CarAdvertServiceSpec extends AsyncFlatSpec with MockitoSugar {

  val createCarAdvertDTO = (carAdvertData: CarAdvertData) => {
    CarAdvertDTO(
      carAdvertData.id.id,
      carAdvertData.title.title,
      carAdvertData.fuel.toString,
      carAdvertData.price,
      carAdvertData.isNew,
      carAdvertData.mileage,
      carAdvertData.firstRegistration.map(_.toString)
    )
  }

  "CarAdvertService" should "return the list of all car advert" in {

    val carAdvertRepository = mock[CarAdvertRepository]
    val carAdvertService    = new CarAdvertService(carAdvertRepository)

    val carAdverts = Seq(
      CarAdvertData(AdvertId(1),
                    AdvertTitle("Audi A4 Avant"),
                    Fuel.Diesel,
                    2000,
                    isNew = false,
                    Some(50000),
                    Some(CarAdvertHelpers.formatDate("2009-03-07"))),
      CarAdvertData(AdvertId(2), AdvertTitle("Toyota Auris")),
      CarAdvertData(AdvertId(3), AdvertTitle("BMW X3"))
    )

    when(carAdvertRepository.listAdverts()) thenReturn Future(carAdverts)

    val expectedCarAds = carAdverts.map(createCarAdvertDTO)

    val actualCarAds: Future[Seq[CarAdvertDTO]] = carAdvertService.listAdverts()
    actualCarAds map { actualResult =>
      assert(actualResult == expectedCarAds)
    }
  }

}
