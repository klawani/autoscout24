package v1.advert

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

class CarAdvertControllerSpec extends PlaySpec with GuiceOneAppPerSuite {

  "CarAdvertController" should {
    "render the list of adverts" in {
      val pathNoSortingParam = s"/api/v1/adverts"
      val request            = FakeRequest(GET, s"$pathNoSortingParam").withHeaders(HOST -> "localhost:9000")
      val result             = route(app, request).get

      status(result) mustEqual OK
      contentAsJson(result).as[Seq[CarAdvertDTO]].size mustEqual 5
    }
  }

  "CarAdvertController" should {
    "render the list of adverts ordered by the title" in {
      val pathWithSortingParam = s"/api/v1/adverts?sortBy=title"
      val request              = FakeRequest(GET, s"$pathWithSortingParam").withHeaders(HOST -> "localhost:9000")
      val result               = route(app, request).get

      status(result) mustEqual OK
      val allAds = contentAsJson(result).as[Seq[CarAdvertDTO]]
      allAds.size mustEqual 5
      allAds.head.title mustEqual "Audi A4 Avant"
    }
  }

  "CarAdvertController" should {
    "fetch an advert by Id" in {
      val adId          = 2
      val pathWithParam = s"/api/v1/adverts/$adId"
      val request       = FakeRequest(GET, s"$pathWithParam").withHeaders(HOST -> "localhost:9000")
      val result        = route(app, request).get

      status(result) mustEqual OK
      val resultAd = contentAsJson(result).as[CarAdvertDTO]

      resultAd.id mustEqual adId
    }
  }
}
