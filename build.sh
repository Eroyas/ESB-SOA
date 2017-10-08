#!/bin/bash
# Script triggering the building process

PATH_TO_SCRIPT=${0%/*}

# Check if the path that called the script is the same as the directory containing the script
if [ "$PATH_TO_SCRIPT" != "." ]; then
    cd "$PATH_TO_SCRIPT"
    echo "Changing directory to $PATH_TO_SCRIPT"
fi

# Build maven project to generate web archive
echo -n "Building maven project..."
mvn clean package -DskipTests

# Check for errors on last call
if [ "$?" = "0" ]; then
    echo "OK"
else
    echo "FAILURE"
    exit 1
fi

set -e
cd deployment
set +e

# Execute Docker Compose
echo -n "Building Docker images..."
docker-compose build

if [ "$?" = "0" ]; then
    echo "OK"
else
    echo "FAILURE"
    exit 1
fi

# Change dir to location before script launch
cd "$OLDPWD"

echo -n ""