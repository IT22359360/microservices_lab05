# Microservices Lab - SE4010

A complete microservices system built with Spring Boot, Docker, and Spring Cloud Gateway.

## Architecture

```
Client (Postman / Browser)
    ↓ HTTP Requests
┌─────────────────────────────┐
│   API Gateway :8080         │
│   /items /orders /payments  │
└──────┬──────────┬──────────┘
       ↓          ↓          ↓
Item :8081  Order :8082  Payment :8083
```

## Services

- **Item Service** (Port 8081) - Manages items inventory
- **Order Service** (Port 8082) - Handles order placement
- **Payment Service** (Port 8083) - Processes payments
- **API Gateway** (Port 8080) - Routes requests to appropriate services

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose

## Setup Instructions

### 1. Start All Services with Docker Compose

**No Maven installation required!** Docker will build everything automatically.

From the project root directory:

```bash
# Build and start all containers
docker compose up --build

# Or start in detached mode (background)
docker compose up --build -d
```

**Note:** If you have `docker-compose` (with hyphen) instead of `docker compose`, use:
```bash
docker-compose up --build
```

### 2. Verify Services are Running

```bash
# View running containers
docker ps

# View logs for a specific service
docker compose logs item-service
docker compose logs order-service
docker compose logs payment-service
docker compose logs api-gateway

# View all logs
docker compose logs

# Follow logs in real-time
docker compose logs -f
```

### 3. Stop Services

```bash
# Stop and remove all containers
docker compose down
```

## API Endpoints

All endpoints should be accessed through the API Gateway at `http://localhost:8080`

### Item Service

- `GET /items` - Get all items
- `POST /items` - Create a new item
  ```json
  {
    "name": "Headphones"
  }
  ```
- `GET /items/{id}` - Get item by ID

### Order Service

- `GET /orders` - Get all orders
- `POST /orders` - Place a new order
  ```json
  {
    "item": "Laptop",
    "quantity": 2,
    "customerId": "C001"
  }
  ```
- `GET /orders/{id}` - Get order by ID

### Payment Service

- `GET /payments` - Get all payments
- `POST /payments/process` - Process a payment
  ```json
  {
    "orderId": 1,
    "amount": 1299.99,
    "method": "CARD"
  }
  ```
- `GET /payments/{id}` - Get payment by ID

## Testing with Postman

1. Open Postman
2. Create a new Collection called "Microservices Lab"
3. Add requests for each endpoint listed above
4. Make sure all services are running before testing

### Sample Test Requests

**Get All Items:**
```
GET http://localhost:8080/items
```

**Create Item:**
```
POST http://localhost:8080/items
Content-Type: application/json

{
  "name": "Headphones"
}
```

**Place Order:**
```
POST http://localhost:8080/orders
Content-Type: application/json

{
  "item": "Laptop",
  "quantity": 2,
  "customerId": "C001"
}
```

**Process Payment:**
```
POST http://localhost:8080/payments/process
Content-Type: application/json

{
  "orderId": 1,
  "amount": 1299.99,
  "method": "CARD"
}
```

## Project Structure

```
lab_05/
├── item-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── order-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── payment-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── api-gateway/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── docker-compose.yml
└── README.md
```

## Troubleshooting

### Services not starting
- Check if ports 8080, 8081, 8082, 8083 are available
- Verify Docker is running: `docker ps`
- Check logs: `docker compose logs [service-name]`

### Gateway routing issues
- Ensure all services are on the same Docker network (`microservices-net`)
- Verify service names match in `docker-compose.yml` and `application.yml`
- Wait a few seconds after starting services for them to fully initialize

### Build errors
- **No Maven needed!** Docker builds everything automatically
- If build fails, try: `docker compose build --no-cache`
- Check Docker has enough resources allocated

## Notes

- All services use in-memory storage (no database required)
- Services communicate through the API Gateway only
- Each service runs independently in its own Docker container
