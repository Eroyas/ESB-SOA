FROM tomee:8-jre-7.0.3-plus

COPY ./car-service.war ./webapps/.

HEALTHCHECK --interval=5s CMD curl --fail http://localhost:8080/ || exit 1

EXPOSE 8080