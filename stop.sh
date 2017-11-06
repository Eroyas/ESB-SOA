#!/bin/bash
# Script triggering the execution process

set -e
cd integration

echo "Stopping containers..."
docker-compose down
echo "Containers stopped"