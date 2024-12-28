# KAM System Project

## Project Overview

The KAM (Key Account Management) System is a comprehensive backend solution designed to manage various aspects of customer interactions, leads, orders, restaurants, users, and analytics. It integrates multiple modules including authentication, order management, lead management, restaurant data management, and scheduling. The project includes various services, APIs, and a database structure to ensure efficient management of key accounts, their activities, and performance metrics.

Key features of the system include:
- User authentication and role-based access
- Lead management with status tracking
- Interaction and order management between key accounts and restaurants
- Call scheduling for follow-up interactions
- Analytics and audit logs for system changes and performance metrics

## System Requirements

- **Java**: 17 or higher
- **Spring Boot**: 2.5 or higher
- **PostgreSQL**: 13 or higher
- **Maven**: 3.8 or higher
- **JDK**: 17 or higher
- **IDE**: IntelliJ IDEA, Eclipse, or similar

## Installation Instructions

Follow these steps to set up the project locally:

1. **Clone the repository**:
    To protect the codebase from public access I've used a personal access token (PAT) for cloning the repository. This token provides read only access to the repository. You can use the following command to clone the repository: 
   ```bash
   git clone https://alpha951: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@github.com/alpha951/key-account-management.git
   cd key-account-management
   ```

2. **Install dependencies** 

    ### Using Docker Compose
    

   If you have IntelliJ Idea IDE installed then simply open the project in IntelliJ and click on the play button to build the application and run the server.
    
    Otherwise you can use maven commands to install the dependencies for this you need to install maven in your local machine.
      ```bash
      mvn clean install
      ```

3. **Database setup**:
    For database system you can run a docker container with below command this will expose the Postgres Container at port 6432 in your local machine. Make sure you have Docker installed else refer [official Docker docs](https://docs.docker.com/engine/install/ubuntu/)
    ```bash
      docker run --rm --name KAMpg-container -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -v kamsystem:/var/lib/postgresql/data -p 6432:5432 -d postgres
    ```
   To access this database in any db viewer like DBeaver use below details:
    ```
    databse_name=postgres
    user=root
    password=root
    connection=jdbc:postgresql://localhost:6432/postgres
   ```
    
    You can also create database in your machine's postgres installation and update the application.properties accordingly.    

   - Configure the `application.properties`  file with your database credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/databas_name
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

4. **Run the application**:
   - Start the server using Maven:
     ```bash
     mvn spring-boot:run
     ```

## Running Instructions

Once the application is set up:

- The application will be available at `http://localhost:8080/`.
- To test various functionalities, you can interact with the provided APIs via tools like **Postman** or **cURL**.


## Database Schema
![Database schema](/docs/database-schema.png)

## API Documentation

### 1. Authentication

- **POST /api/auth/login**: Login endpoint to authenticate users.
  - **Request**: 
    ```json
    {
      "mobile": "9999999991",
      "password": "password"
    }
    ```
  - **Response**: 
    ```json
    {
      "token": "jwt_token_here"
    }
    ```
    
> For all below api use the jwt token in 'Authorization' header as they are protected routes

### 2. Lead Management

- **GET /api/leads**: Retrieve all leads.
- **POST /api/leads**: Create a new lead.
  - **Request**: 
    ```json
    {
      "restaurantId": 123,
      "createdBy": 456,
      "leadStatus": 1
    }
    ```

### 3. Interaction

- **GET /api/interactions**: Get all interactions.
- **POST /api/interactions**: Create a new interaction.
  - **Request**:
    ```json
    {
      "callerId": 123,
      "leadId": 456,
      "interactionDetails": "Details about the interaction",
      "interactionType": "CALL"
    }
    ```

### 4. Call Scheduling

- **GET /api/call-schedules**: Get all call schedules.
- **POST /api/call-schedules**: Schedule a new call.
  - **Request**:
    ```json
    {
      "leadId": 789,
      "startDate": "2024-01-01T10:00:00",
      "endDate": "2024-01-01T11:00:00",
      "timeZone": "Asia/Kolkata"
    }
    ```

### 5. Orders

- **GET /api/orders**: Retrieve all orders.
- **POST /api/orders**: Place a new order.
  - **Request**:
    ```json
    {
      "leadId": 123,
      "restaurantId": 456,
      "orderId": "uuid_here",
      "amount": 5000
    }
    ```

### 6. Analytics

- **GET /api/analytics/metrics**: Retrieve key metrics.
- **POST /api/analytics/metrics**: Submit new metric data.
  - **Request**:
    ```json
    {
      "metricName": "Order Frequency",
      "metricValue": 100,
      "metricValueType": "COUNT",
      "timeframe": "monthly",
      "year": 2024,
      "month": 12,
      "day": 25
    }
    ```

## Sample Usage Examples

1. **Login**: To authenticate and receive a JWT token:
   ```bash
   POST /api/auth/login
   ```
   Body:
   ```json
   {
     "email": "user@example.com",
     "password": "password"
   }
   ```

2. **Create a Lead**:
   ```bash
   POST /api/leads
   ```
   Body:
   ```json
   {
     "restaurantId": 123,
     "createdBy": 456,
     "leadStatus": 1
   }
   ```

3. **Schedule a Call**:
   ```bash
   POST /api/call-schedules
   ```
   Body:
   ```json
   {
     "leadId": 789,
     "startDate": "2024-01-01T10:00:00",
     "endDate": "2024-01-01T11:00:00",
     "timeZone": "Asia/Kolkata"
   }
   ```

4. **Place an Order**:
   ```bash
   POST /api/orders
   ```
   Body:
   ```json
   {
     "leadId": 123,
     "restaurantId": 456,
     "orderId": "uuid_here",
     "amount": 5000
   }
   ```

## Conclusion

This project is designed to streamline the management of key accounts, interactions, and analytics, making it easier for businesses to manage their customer relationships effectively. By following the setup, running instructions, and utilizing the API endpoints, you can integrate the system into your business workflow seamlessly.