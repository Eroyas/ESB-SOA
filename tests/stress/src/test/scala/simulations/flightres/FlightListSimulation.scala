package simulations.flightres;

import java.util.UUID

import dockerized.DockerizedTest
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.language.postfixOps
import scala.concurrent.duration._


class FlightListSimulation extends DockerizedTest{

  private val host = getDockerHost
  private val port = "9090"
  private val serviceName = "/tta-service-rpc"

  val httpConf = http.baseURL("http://" + host + ":" + port + serviceName)
    .acceptHeader("application/xml")
    .header("Content-Type", "application/xml")

  val stressSample = scenario("Getting flight from a point to another")
    .repeat(10) {
      exec(session =>
        session.set("ssn", UUID.randomUUID().toString)
      )
        .exec(http(" Booking a flight from Paris using a simple itinerary request")
          .post("/FlightBookingService")
          .body(StringBody(session => retrieveFlight(session)))
          .check(status.is(200))
        )
    }

  def retrieveFlight(session: Session): String = {
    val ssn = session("ssn").as[String]
    raw"""<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soa="http://informatique.polytech.unice.fr/soa1/team/3/flightres/"><soapenv:Header/><soapenv:Body><soa:simpleReservation><id>palatine-234</id><departureTime>2017-10-25</departureTime><originCountry>Paris</originCountry><destinationCountry>Nice</destinationCountry></soa:simpleReservation></soapenv:Body></soapenv:Envelope>""""
  }


  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}