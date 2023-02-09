
# Customer Rewards

Requirement:

A retailer offers a rewards program to its customers awarding points based on each recorded purchase as follows:

 
For every dollar spent over $50 on the transaction, the customer receives one point.
In addition, for every dollar spent over $100, the customer receives another point.
Ex: for a $120 purchase, the customer receives
(120 - 50) x 1 + (120 - 100) x 1 = 90 points

Given a record of every transaction during a three-month period, calculate the reward points earned for each customer per month and total. 




## Implementation
Implemented using Spring Boot, SpringJpa, H2 DB, Embedded Tomcat, Java11.0.16

DB Tables:
*   CUSTOMER
*   TRANSACTION
*   REWARD
  
## APIs Implemented

In order to see the Rewards data, Create Customers, Create transactions and call Getter API's to get Rewards in information.

*    Create Customer:-  POST /customer  
*    Create Transaction:-POST /customer/transaction  
*    Get Rewards Info:- GET /customer/rewards
*    Get Rewards by customer:- GET /customer/rewards

## Application URL's
    
* Swagger: http://localhost:8080/rewards/swagger-ui/index.html
* API Docs: http://localhost:8080/rewards/v3/api-docs
* Actuator: http://localhost:8080/actuator
* Health Check: http://localhost:8080/actuator/health
* Metrics: http://localhost:8080/actuator/metrics
* Prometheus: http://localhost:8080/actuator/prometheus


## Features

- Implemented Code Coverage using Jacoco
- Configured Health Check and Metrics
- Implemented Dockerization
- JUnit Test Cases


## Prerequisites for Running Locally

*  Install IntelliJ IDE or Eclipse recommended
*  Install Docker to run application in Docker (Optional)
*  OpenJdk 11 or above supported by Jacoco
*  maven

## Run Application Instructions
*  Clone the project
*  Import project into IDE
*  Build using maven
*  Run the application
*  Code Coverage Report Path: retailer-customer-rewards/target/site/jacoco/index.html

## Run Application in Docker Instructions
*  Build the project using maven
*  Go to Docker Terminal
*  Go to application root directory
*  build image using command: docker build -t customer-rewards-image
*  run the container using command: docker run -p 8081:8080 customer-rewards-image



