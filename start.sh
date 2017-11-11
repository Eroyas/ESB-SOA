#!/bin/bash
# Script triggering the execution process

set -e
cd integration

echo "Starting containers..."
docker-compose up -d

echo "# Import flights for external services"

docker-compose exec flights-database /bin/bash -c "until [ isMongoReady ]; do sleep 20; isMongoReady = pgrep mongod; done"

docker-compose exec flights-database /bin/bash -c "mongoimport --db flight --collection flights --jsonArray --file /external-flights-service/resources/flights.json"


echo "Containers started detached."