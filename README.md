# KAM System Project

## Project Overview

The KAM (Key Account Management) System is a comprehensive backend solution designed to manage various aspects of customer interactions, leads, orders, restaurants, users, and analytics. It integrates multiple modules including authentication, order management, lead management, restaurant data management, and scheduling. The project includes various services, APIs, and a database structure to ensure efficient management of key accounts, their activities, and performance metrics.

Key features of the system include:
- User authentication and role-based access
- Lead management with status tracking
- Interaction and order management between key accounts and restaurants
- Call scheduling for follow-up interactions
- Setup of cron jobs to get analytics on various metrics for different time frame with persisting historical metrics in database.
- Storing KAM related changes in audit change log table for record purpose.

## System Requirements

- **Java**: 17 or higher
- **Spring Boot**: 2.5 or higher
- **PostgresSQL**: 13 or higher
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

2. **Setting up the project** 

### Single command setup using Docker Compose (Recommended for development and testing)
Simply run below command this will complete the whole setup including downloading dependencies, running schema.sql and data.sql for seed data
and spinning up the server. This will expose the database on port 6432 on your local machine.

```bash
    docker compose up --build 
```

   > If you are using old compose command then use
```bash
   docker-compose up --build
```
   
To run containers in detach mode use `d` flag
```bash
  docker compose up -d
```

To stop and remove the containers and delete the volume
```bash
  docker compose down -v
```
  
### Running the application in your machine natively
You will need to setup the database first

 **Database setup**:
For database system you can run a docker container with below command this will expose the Postgres Container at port 6432 in your local machine. Make sure you have Docker installed else refer [official Docker docs](https://docs.docker.com/engine/install/ubuntu/)

```bash
  docker run --rm --name KAMpg-container -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -v kamsystem:/var/lib/postgresql/data -p 6432:5432 -d postgres
```
To access this database in any db viewer like DBeaver use below details:

```text
    databse_name=postgres
    user=root
    password=root
    connection=jdbc:postgresql://localhost:6432/postgres
```

Now you can manually run `/src/main/resorces/db/schem.sql` and `data.sql` to create schema and populate seed data into your database instance. 
    
You can also create database in your machine's postgres installation and update the application.properties accordingly.    

- Configure the `application.properties`  file with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/databas_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### Using IntelliJ Idea
If you have IntelliJ Idea IDE installed then simply open the project in IntelliJ and click on the play button to build the application and run the server.
    
#### Using maven command
Otherwise you can use maven commands to install the dependencies for this you need to install maven in your local machine.

  ```bash
    mvn clean install
  ```



## Running Instructions

Once the application is set up:

- The application will be available at `http://localhost:8080/`.
- To test various functionalities, you can interact with the provided APIs via tools like **Postman** or **cURL**.


## Database Schema
![Database schema](/docs/database-schema.png)

## API Documentation

The complete API docs can be found in this [postman collection](https://www.postman.com/spaceflight-candidate-61154118/workspace/kam-apis-demo/collection/40691637-00317fdc-204b-470d-a6a0-57dcd5e8d8cf?action=share&creator=40691637&active-environment=40691637-9f8791df-d7cf-47bc-828b-801c5882f115). I've setup two different environments for testing. Please select the `prod` env for testing the API without any additional setup.
For those APIs which perform some kind of Data manipulation operations use some new data in request body instead of existing one present in the postman collection. As existing data may give error responses. 

> For Admin user login using below credentials :

```json
{
    "mobile" : "9999999991",
    "password": "password"
}
```

> For KAM User login using below credentials :
```json
{
    "mobile" : "9999999992",
    "password": "password"
}
```

## Conclusion

This project is designed to streamline the management of key accounts, interactions, and analytics, making it easier for businesses to manage their customer relationships effectively. By following the setup, running instructions, and utilizing the API endpoints, you can integrate the system into your business workflow seamlessly.