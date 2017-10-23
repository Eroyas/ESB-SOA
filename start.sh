#!/bin/bash
# Script triggering the execution process

set -e
cd integration

echo "Starting containers..."
docker-compose up -d
echo "Containers started detached."