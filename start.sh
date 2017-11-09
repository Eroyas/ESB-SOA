#!/bin/bash
# Script triggering the execution process

set -e
cd integration

echo "Starting containers..."
docker-compose up -d

echo "# Import flights for external services"
#TODO check that the mongo db is up and running inside the container before import
docker-compose exec flights-database /bin/bash -c "mongoimport --db flight --collection flights --jsonArray --file /external-flights-service/resources/flights.json"

echo "Containers started detached."