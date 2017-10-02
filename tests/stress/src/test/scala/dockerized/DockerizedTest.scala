package dockerized

import io.gatling.core.Predef._

// Return "localhost" unless there is an environment variable named DOCKER_HOST_IP
// Should the env variable exist, it should contain the adress of the docker machine host
abstract class DockerizedTest extends Simulation{
  def getDockerHost: String = {
    scala.util.Properties.envOrElse("DOCKER_HOST_IP", "localhost" )
  }
}
