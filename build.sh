#!/bin/bash

echo "Building all microservices..."

echo "Building Item Service..."
cd item-service
mvn clean package -DskipTests
cd ..

echo "Building Order Service..."
cd order-service
mvn clean package -DskipTests
cd ..

echo "Building Payment Service..."
cd payment-service
mvn clean package -DskipTests
cd ..

echo "Building API Gateway..."
cd api-gateway
mvn clean package -DskipTests
cd ..

echo "All services built successfully!"
echo "Run 'docker-compose up --build' to start all services"
