package simulations.hotel

import dockerized.DockerizedTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.language.postfixOps
import scala.concurrent.duration._

class GetHotelReservationSimulation extends DockerizedTest {

  private val host = getDockerHost
  private val port = "9080"
  private val serviceName = "/tta-car-and-hotel"

  val httpConf = http.baseURL("http://" + host + ":" + port + serviceName)
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")

  val stressSample = scenario("Listing the hotel for an customer reservation in Paris for an Ibis hotel between the 01-10-2017 to 03-10-2017")
    .repeat(10) {
      exec(http("Listing the hotel for an customer reservation in Paris for an Ibis hotel between the 01-10-2017 to 03-10-2017 with at least one hotel")
        .get("/hotels/Paris/Ibis/01-10-2017/03-10-2017")
        .check(status.is(200))
      )
    }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}
