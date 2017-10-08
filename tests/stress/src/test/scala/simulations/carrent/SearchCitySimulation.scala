package simulations.carrent

import dockerized.DockerizedTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.language.postfixOps
import scala.concurrent.duration._

class SearchCitySimulation extends DockerizedTest{
  private val host = getDockerHost
  private val port = "9080"
  private val serviceName = "/tta-car-and-hotel/car-rental/search"

  val httpConf = http.baseURL("http://" + host + ":" + port + serviceName)
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")

  val stressSample = scenario("Listing agencies for a given city")
    .repeat(10) {
      exec(http("Listing the agencies of the city of Paris")
        .get("/Paris")
        .check(status.is(200))
      )
    }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}
