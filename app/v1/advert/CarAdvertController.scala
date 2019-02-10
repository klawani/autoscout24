package v1.advert

import javax.inject.Inject
import play.api.Logger
import play.api.libs.json.{ JsError, JsValue, Json }
import play.api.mvc.{ Action, AnyContent }

import scala.concurrent.{ ExecutionContext, Future }

class CarAdvertController @Inject()(cc: CarAdvertControllerComponents)(
    implicit ec: ExecutionContext
) extends CarAdvertBaseController(cc) {

  private val logger = Logger(getClass)

  def list(sortByField: Option[String]): Action[AnyContent] = CarAdvertAction.async {
    implicit request =>
      logger.trace("listAdverts: " + request)
      carAdvertService.listAdverts(sortByField).map { ads =>
        Ok(Json.toJson(ads))
      }
  }

  def getAdvert(adId: Int): Action[AnyContent] = CarAdvertAction.async { implicit request =>
    logger.trace("getAdvert: " + request)
    carAdvertService.getAdvert(AdvertId(adId)).map { ad =>
      Ok(Json.toJson(ad))
    }
  }

  def createAdvert: Action[JsValue] = CarAdvertAction.async(parse.json) { implicit request =>
    logger.trace("createAdvert: " + request)
    val carAdvertResult = request.body.validate[CarAdvertDTO]
    carAdvertResult.fold(
      errors => {
        Future.successful(
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
        )
      },
      carAdvert => {
        carAdvertService
          .createAdvert(carAdvert)
          .map(adId => Ok(Json.toJson(adId)))
      }
    )
  }

}
