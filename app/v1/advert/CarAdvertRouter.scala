package v1.advert

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class CarAdvertRouter @Inject()(controller: CarAdvertController) extends SimpleRouter {

  override def routes: Routes = {

    case GET(p"/adverts" ? q_o"sortBy=$field") => {
      controller.list(field)
    }
    case GET(p"/adverts/${int(id) }") =>
      controller.getAdvert(id)
  }
}
