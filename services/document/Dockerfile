FROM tomee:8-jre-7.0.3-plus
MAINTAINER Sébastien Mosser (mosser@i3s.unice.fr)

# Build with : docker build -t petitroll/tcs-doc .
# Publish with: docker push petitroll/tcs-doc

COPY ./target/tta-reservation.war ./webapps/.

EXPOSE 8080