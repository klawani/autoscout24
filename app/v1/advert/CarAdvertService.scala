package v1.advert

import javax.inject.Inject
import play.api.MarkerContext

import scala.concurrent.{ ExecutionContext, Future }

class CarAdvertService @Inject()(
    carAdvertRepository: CarAdvertRepository
)(implicit ec: ExecutionContext) {

  def listAdverts(sortBy: Option[String])(implicit mc: MarkerContext): Future[Seq[CarAdvertDTO]] =
    carAdvertRepository
      .listAdverts(sortBy)
      .map { ads =>
        ads.map(CarAdvertHelpers.createCarAdvertDTO)
      }

  def getAdvert(adId: AdvertId)(implicit mc: MarkerContext): Future[Option[CarAdvertDTO]] =
    carAdvertRepository
      .getAdvert(adId)
      .map(_.map(CarAdvertHelpers.createCarAdvertDTO))

  def createAdvert(carAdvertDTO: CarAdvertDTO)(implicit mc: MarkerContext): Future[Int] =
    carAdvertRepository
      .createAdvert(CarAdvertHelpers.createCarAdvertFromDTO(carAdvertDTO))
      .map(adId => adId.id)
}
