#!/bin/bash
# Script triggering the testing process

echo -n "Running tests..."

mvn test verify

echo -n "Test suite executed."