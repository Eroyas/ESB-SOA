#!/bin/bash
# Script triggering the execution process

cd deployment

echo -n "Starting containers..."
docker-compose up -d

echo -n ""
echo -n "Containers started detached."


