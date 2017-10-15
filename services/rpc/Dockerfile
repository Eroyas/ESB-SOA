FROM tomee:8-jre-7.0.3-plus

COPY ./target/tta-service-rpc.war ./webapps/.

# Copy Data file to file system
RUN mkdir data
COPY ./target/data/flights-data.json ./data/.

HEALTHCHECK --interval=5s CMD curl --fail http://localhost:8080/ || exit 1

EXPOSE 8080
