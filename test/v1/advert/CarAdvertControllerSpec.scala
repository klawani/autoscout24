package v1.advert

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

class CarAdvertControllerSpec extends PlaySpec with GuiceOneAppPerSuite {

  val expectedTotalInitialAds = 5

  "CarAdvertController" should {
    "render the list of adverts" in {
      val pathNoSortingParam = s"/api/v1/adverts"
      val request            = FakeRequest(GET, s"$pathNoSortingParam").withHeaders(HOST -> "localhost:9000")
      val result             = route(app, request).get

      status(result) mustEqual OK
      contentAsJson(result).as[Seq[CarAdvertDTO]].size mustEqual expectedTotalInitialAds
    }
  }

  "CarAdvertController" should {
    "render the list of adverts ordered by the title" in {
      val pathWithSortingParam = s"/api/v1/adverts?sortBy=title"
      val request              = FakeRequest(GET, s"$pathWithSortingParam").withHeaders(HOST -> "localhost:9000")
      val result               = route(app, request).get

      status(result) mustEqual OK
      val allAds = contentAsJson(result).as[Seq[CarAdvertDTO]]
      allAds.size mustEqual expectedTotalInitialAds
      allAds.head.title mustEqual "Audi A4 Avant"
    }
  }

  "CarAdvertController" should {
    "fetch an advert by Id" in {
      val expectedId    = 2
      val pathWithParam = s"/api/v1/adverts/$expectedId"
      val request       = FakeRequest(GET, s"$pathWithParam").withHeaders(HOST -> "localhost:9000")
      val result        = route(app, request).get

      status(result) mustEqual OK
      val resultAd = contentAsJson(result).as[CarAdvertDTO]

      resultAd.id mustEqual expectedId
    }
  }

  "CarAdvertController" should {
    "create an advert from Json file" in {
      val newCarAdvertAsJson = s"""{
        "id": 8,
        "title": "Range Rover",
        "fuel": "diesel",
        "price": 0,
        "isNew": true
        }"""

      val expectedId  = 6
      val pathNoParam = s"/api/v1/advert"
      val request = FakeRequest(POST, s"$pathNoParam")
        .withHeaders(HOST -> "localhost:9000")
        .withHeaders(CONTENT_TYPE -> "application/json")
        .withBody(newCarAdvertAsJson)

      val result = route(app, request).get

      status(result) mustEqual OK
      val resultAd = contentAsJson(result).as[Int]

      resultAd mustEqual expectedId

      val request2 = FakeRequest(GET, s"/api/v1/adverts").withHeaders(HOST -> "localhost:9000")
      val result2  = route(app, request2).get

      status(result2) mustEqual OK
      contentAsJson(result2).as[Seq[CarAdvertDTO]].size mustEqual (expectedTotalInitialAds + 1)
    }
  }

  "CarAdvertController" should {
    "update an advert from Json file" in {
      val updateAdvertAsJson = s"""{
        "id": 3,
        "title": "Range Rover",
        "fuel": "diesel",
        "price": 0,
        "isNew": true
        }"""

      val toUpdateAdvertId  = 3
      val advertRequestPath = s"/api/v1/adverts/$toUpdateAdvertId"

      val existingRequest = FakeRequest(GET, s"$advertRequestPath")
        .withHeaders(HOST -> "localhost:9000")
      val existingResult = route(app, existingRequest).get
      status(existingResult) mustEqual OK

      val existingResultAd = contentAsJson(existingResult).as[CarAdvertDTO]

      val updateRequest = FakeRequest(PUT, s"/api/v1/advert")
        .withHeaders(HOST -> "localhost:9000")
        .withHeaders(CONTENT_TYPE -> "application/json")
        .withBody(updateAdvertAsJson)

      val updateResult = route(app, updateRequest).get
      status(updateResult) mustEqual OK
      val updateResultAd = contentAsJson(updateResult).as[Int]

      updateResultAd mustEqual toUpdateAdvertId

      val checkUpdateRequest = FakeRequest(GET, s"$advertRequestPath")
        .withHeaders(HOST -> "localhost:9000")
      val checkUpdateResult = route(app, checkUpdateRequest).get

      status(checkUpdateResult) mustEqual OK
      val checkUpdateResultAd = contentAsJson(checkUpdateResult).as[CarAdvertDTO]

      checkUpdateResultAd.title must not equal existingResultAd.title
    }
  }

  "CarAdvertController" should {
    "delete an advert by id" in {

      val toDeleteAdvertId = 3

      val deleteRequest = FakeRequest(DELETE, s"/api/v1/advert/$toDeleteAdvertId")
        .withHeaders(HOST -> "localhost:9000")

      val deleteResult = route(app, deleteRequest).get
      status(deleteResult) mustEqual OK
      val deleteResultAd = contentAsJson(deleteResult).as[Int]

      deleteResultAd mustEqual toDeleteAdvertId

      val checkDeleteRequest = FakeRequest(GET, s"/api/v1/adverts/$toDeleteAdvertId")
        .withHeaders(HOST -> "localhost:9000")
      val checkDeleteResult = route(app, checkDeleteRequest).get

      status(checkDeleteResult) mustEqual OK
      contentAsString(checkDeleteResult) mustEqual "null"
    }
  }
}
