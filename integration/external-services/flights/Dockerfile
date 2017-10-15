FROM tomee:8-jre-7.0.3-plus

WORKDIR /usr/local/tomee/

COPY ./flights-service.war ./webapps/.

## Configuring the network.properties file (default values are provided)
ENV db_host=localhost
ENV db_port=27017

HEALTHCHECK --interval=5s CMD curl --fail http://localhost:8080/ || exit 1

EXPOSE 8080