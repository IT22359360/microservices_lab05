# Testing Guide

## Quick Start

**No Maven installation required!** Docker builds everything automatically.

1. **Start all services:**
   ```bash
   docker compose up --build
   ```
   
   Or in detached mode (background):
   ```bash
   docker compose up --build -d
   ```
   
   **Note:** If you have `docker-compose` (with hyphen), use that instead.

3. **Wait for all services to start** (check logs: `docker-compose logs`)

4. **Test endpoints** through API Gateway at `http://localhost:8080`

## Postman Collection Setup

Create a new collection in Postman called "Microservices Lab" with the following requests:

### Item Service Endpoints

1. **GET All Items**
   - Method: GET
   - URL: `http://localhost:8080/items`
   - Expected: `["Book", "Laptop", "Phone"]`

2. **POST Create Item**
   - Method: POST
   - URL: `http://localhost:8080/items`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
     ```json
     {
       "name": "Headphones"
     }
     ```
   - Expected: `"Item added: Headphones"`

3. **GET Item by ID**
   - Method: GET
   - URL: `http://localhost:8080/items/0`
   - Expected: `"Book"`

### Order Service Endpoints

1. **GET All Orders**
   - Method: GET
   - URL: `http://localhost:8080/orders`
   - Expected: `[]` (empty initially)

2. **POST Place Order**
   - Method: POST
   - URL: `http://localhost:8080/orders`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
     ```json
     {
       "item": "Laptop",
       "quantity": 2,
       "customerId": "C001"
     }
     ```
   - Expected: Order object with `id`, `status: "PENDING"`, and your data

3. **GET Order by ID**
   - Method: GET
   - URL: `http://localhost:8080/orders/1`
   - Expected: Order object

### Payment Service Endpoints

1. **GET All Payments**
   - Method: GET
   - URL: `http://localhost:8080/payments`
   - Expected: `[]` (empty initially)

2. **POST Process Payment**
   - Method: POST
   - URL: `http://localhost:8080/payments/process`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
     ```json
     {
       "orderId": 1,
       "amount": 1299.99,
       "method": "CARD"
     }
     ```
   - Expected: Payment object with `id`, `status: "SUCCESS"`, and your data

3. **GET Payment by ID**
   - Method: GET
   - URL: `http://localhost:8080/payments/1`
   - Expected: Payment object

## Testing Workflow

1. Start with Item Service - verify you can get items and add new ones
2. Create an order using Order Service
3. Process payment for that order using Payment Service
4. Verify all data persists during the session (in-memory storage)

## Troubleshooting

- **502 Bad Gateway**: Services might not be fully started. Wait 10-20 seconds and try again.
- **Connection refused**: Check if Docker containers are running: `docker ps`
- **404 Not Found**: Verify the endpoint path matches exactly (case-sensitive)
- **500 Internal Server Error**: Check service logs: `docker compose logs [service-name]`

## Direct Service Access (for debugging)

You can also access services directly (bypassing gateway):
- Item Service: `http://localhost:8081/items`
- Order Service: `http://localhost:8082/orders`
- Payment Service: `http://localhost:8083/payments`

But remember: **All requests should go through the API Gateway** for the lab requirements.
