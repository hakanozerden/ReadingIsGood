# Reading Is Good

---
## Table of Contents

* [About the Microservice](#about-the-microservice)
* [History](#history)
* [Getting Started](#getting-started)
    * [Tech Stack](#tech-stack)
    * [Prerequisites](#prerequisites)
* [Configurations](#configurations)
    * [Application](#application-configurations)
    * [Database](#database-configurations)    
* [Usage](#usage)
    * [Authenticate](#authenticate)
    * [Create Book](#create-book)
    * [Update Book](#update-book)
    * [Create Customer](#create-customer)
    * [Get Orders Of Customer](#get-orders-of-customer)
    * [Create Order](#create-order)
    * [Get Order Detail](#get-order-detail)
    * [Search Orders By Date Interval](#search-orders-by-date-interval)  
    * [Customer's Monthly Statistics](#customers-monthly-statistics)
* [Error Responses](#error-responses)
* [Run-Build & Test](#run-build--test)
    * [Docker Compose](#docker-compose)
    * [Maven](#maven)
    * [TDD](#tdd)	
    * [Swagger UI](#swagger)		
---

## About the Microservice

ReadingIsGood is an online books retail firm which operates only on the Internet. Main 
target of ReadingIsGood is to deliver books from its one centralized warehouse to their 
customers within the same day. That is why stock consistency is the first priority for their 
vision operations.

---
## History

Version : <b>v1</b> Initial version of microservice.

---
## Getting Started

### Tech Stack

- Java 11
- Spring Framework
- mongoDB  
- Docker
- JUnit
---
### Prerequisites

The microservice runs with Java 11. It requires mondoDB. Maven or Docker needed to build service.

---
## Configurations
#### Application Configurations

Locale configurations can be found in "application.properties" file.

* `swagger.enabled`: (<b>true</b>) Enable or disable swagger ui.

#### Database Configurations

Locale database configurations for mongoDB Server. All configuration values will change while Docker installs the application depending on docker-compose.yml file.

* `spring.data.mongodb.host` : Default database host is "localhost". 
* `spring.data.mongodb.port` : Default database port is "27017". 
* `spring.data.mongodb.username` : Default database username is "readingisgoodUser". 
* `spring.data.mongodb.password` : Default database password is "password". 
* `spring.data.mongodb.database` : Default database name is "readingisgood". 
* `spring.data.mongodb.auto-index-creation` : It creates indexes while generating collections. Default value is "true". 

---
## Usage

### Authenticate

Microservice endpoints secured. Before sending requests, JWT needs to be add as a bearer token to the header parameters.

####Credentials
Default admin user is; <b>admin</b>, password: <b>123456</b>

```
GET 
/api/auth?username=admin&password=123456
```

---
### Create Book
```
POST 
/api/v1/book
```

```json
{
  "author": "Author Jack",
  "price": 23.50,
  "title": "Title of Jack's Book",
  "unitsInStock": 23
}
```

---
### Update Book
```
PATCH 
/api/v1/book/{bookId}
```

```json
{
    "unitsInStock" : 567
}
```

---
### Create Customer
```
POST 
/api/v1/customer
```

```json
{
  "email": "customer@email.com",
  "name": "Customer First",
  "phoneNumber": "905551231213",
  "surname": "Surname"
}
```

---
### Get Orders Of Customer
```
GET 
/api/v1/customer/orders/{customerId}
```

---
### Create Order
```
POST 
/api/v1/order
```

```json
{
  "bookId": "bookId",
  "customerId": "customerId",
  "quantity": 5
}
```

---
### Get Order Detail
```
GET 
/api/v1/order/{id}
```

---
### Search Orders By Date Interval

"startDate" and "endDate" format must be "yyyy-MM-dd" ex. "2021-01-01" 

```
GET 
/api/v1/order/search/{startDate}/{endDate}
```

---
### Customer's Monthly Statistics

```
GET 
/api/v1/statistic
```

### Error Responses

---
| Code | Description |
| ------ | ------ |
|	400 Bad Request	|	Entity with given id not found.	|
|	406 Not Acceptable	|	Insufficient stock.	|
|	400 Bad Request	|	Customer not found.	|
|	400 Bad Request	|	Order not found.	|
|	400 Bad Request	|	Book not found.	|
|	400 Bad Request	|	Invalid JWT signature.	|
|	400 Bad Request	|	Invalid JWT.	|
|	400 Bad Request	|	JWT expired.	|
|	400 Bad Request	|	JWT is unsupported.	|
|	401 Unauthorized	|	Unauthorized.	|
|	401 Unauthorized	|	Validation failed. Please check the username and password and try again.	|
|	400 Bad Request	|	Total stock can not be less than 0.	|

### Run-Build & Test

---
There are 2 ways of run & build the application.

#### Docker Compose

For docker compose usage, just run "runDocker.sh" or
```ssh
$ cd order
$ docker-compose up --build
```

*Default {$PORT} of microservice on docker is : 8991*

#### Maven

For maven usage just run "runMaven.sh" or

```ssh
$ cd order
$ mvn clean install
$ mvn spring-boot:run
```
*Default {$PORT} of microservice is : 8991*

#### TDD

EmbeddedMongoDB used while running integration tests. "runTests.sh" can be use to run the tests.

#### Swagger

There is swagger in the service, it can be accessed as follows after the application runs for testing. 

`http://localhost:${PORT}/swagger-ui/`


