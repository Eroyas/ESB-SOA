FROM tomee:8-jre-7.0.3-plus

COPY ./target/tta-car-and-hotel.war ./webapps/.

# Copy Data file to file system
RUN mkdir data
COPY ./target/data/Location-Agencies.json ./data/.
COPY ./target/data/Hotels_DB.json ./data/.

HEALTHCHECK --interval=5s CMD curl --fail http://localhost:8080/ || exit 1

EXPOSE 8080