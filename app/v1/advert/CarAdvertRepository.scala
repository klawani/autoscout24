package v1.advert

import akka.actor.ActorSystem
import javax.inject.{ Inject, Singleton }
import play.api.{ Logger, MarkerContext }
import play.api.libs.concurrent.CustomExecutionContext

import scala.concurrent.Future

class CarAdvertExecutionContext @Inject()(actorSystem: ActorSystem)
    extends CustomExecutionContext(actorSystem, "repository.dispatcher")

trait CarAdvertRepository {

  def listAdverts(sortBy: Option[String])(implicit mc: MarkerContext): Future[Seq[CarAdvertData]]

  def getAdvert(adId: AdvertId)(implicit mc: MarkerContext): Future[Option[CarAdvertData]]

  def createAdvert(data: CarAdvertData)(implicit mc: MarkerContext): Future[AdvertId]

  def updateAdvert(data: CarAdvertData)(implicit mc: MarkerContext): Future[AdvertId]

  def deleteAdvert(id: AdvertId)(implicit mc: MarkerContext): Future[AdvertId]

}

@Singleton
class CarAdvertRepositoryImpl @Inject()()(implicit ec: CarAdvertExecutionContext)
    extends CarAdvertRepository {

  private val logger = Logger(this.getClass)

  private val carAdverts = scala.collection.mutable.ListBuffer(
    CarAdvertData(AdvertId(1),
                  AdvertTitle("Audi A4 Avant"),
                  Fuel.Diesel,
                  2000,
                  isNew = false,
                  Some(50000),
                  Some(CarAdvertHelpers.formatDate("2009-05-02"))),
    CarAdvertData(AdvertId(2), AdvertTitle("Toyota Auris")),
    CarAdvertData(AdvertId(3), AdvertTitle("BMW X3")),
    CarAdvertData(AdvertId(4), AdvertTitle("Mercedes Benz")),
    CarAdvertData(AdvertId(5), AdvertTitle("VW Polo"))
  )

  override def listAdverts(
      sortBy: Option[String]
  )(implicit mc: MarkerContext): Future[Seq[CarAdvertData]] =
    Future {
      logger.trace(s"listAdverts: ")
      sortBy match {
        case Some(sort) => {
          sort.toLowerCase match {
            case "title" => carAdverts.sorted(CarAdvertData.orderingByTitle)
            case _       => carAdverts.sorted
          }
        }
        case _ => carAdverts.sorted
      }
    }

  override def getAdvert(adId: AdvertId)(
      implicit mc: MarkerContext
  ): Future[Option[CarAdvertData]] = ???

  override def createAdvert(data: CarAdvertData)(implicit mc: MarkerContext): Future[AdvertId] = ???

  override def updateAdvert(data: CarAdvertData)(implicit mc: MarkerContext): Future[AdvertId] = ???

  override def deleteAdvert(id: AdvertId)(implicit mc: MarkerContext): Future[AdvertId] = ???
}
