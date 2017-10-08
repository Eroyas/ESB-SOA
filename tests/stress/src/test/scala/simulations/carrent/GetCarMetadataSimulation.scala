package simulations.carrent

import dockerized.DockerizedTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.language.postfixOps
import scala.concurrent.duration._

class GetCarMetadataSimulation extends DockerizedTest{
  private val host = getDockerHost
  private val port = "9080"
  private val serviceName = "/tta-car-and-hotel/car-rental/car"

  val httpConf = http.baseURL("http://" + host + ":" + port + serviceName)
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")

  val stressSample = scenario("Get metadata for a given car")
    .repeat(10) {
      exec(http("Get the metadata of the car with UID 1")
        .get("/1")
        .check(status.is(200))
      )
    }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}
