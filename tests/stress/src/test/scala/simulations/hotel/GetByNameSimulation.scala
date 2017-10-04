package simulations.hotel

import dockerized.DockerizedTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.language.postfixOps
import scala.concurrent.duration._

class GetByNameSimulation extends DockerizedTest {

  private val host = getDockerHost
  private val port = "9080"
  private val serviceName = "/tta-car-and-hotel"

  val httpConf = http.baseURL("http://" + host + ":" + port + serviceName)
    .acceptHeader("application/json")
    .header("Content-Type", "application/json")

  val stressSample = scenario("Listing all available Ibis hotels")
    .repeat(10) {
      exec(http("Listing all available Ibis hotels with at least one hotel")
        .get("/hotels/name/Ibis")
        .check(status.is(200))
      )
    }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}
