package dockerized;

public class DockerizedTest {
    // Return "localhost" unless there is an environment variable named DOCKER_HOST_IP
    // Should the env variable exist, it should contain the adress of the docker machine host
    public static String getDockerHost() {
        return System.getenv("DOCKER_HOST_IP") != null ? System.getenv("DOCKER_HOST_IP") : "localhost";
    }
}
