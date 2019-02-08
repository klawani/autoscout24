package v1.advert

import javax.inject.Inject
import play.api.MarkerContext

import scala.concurrent.{ ExecutionContext, Future }

class CarAdvertService @Inject()(
    carAdvertRepository: CarAdvertRepository
)(implicit ec: ExecutionContext) {

  def listAdverts()(implicit mc: MarkerContext): Future[Seq[CarAdvertDTO]] =
    carAdvertRepository.listAdverts().map { ads =>
      ads.map(createCarAdvertDTO)
    }

  private def createCarAdvertDTO(carAdvertData: CarAdvertData): CarAdvertDTO =
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
