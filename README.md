# iCommerce Application using Microservices Architecture
**_A e-commerce application using micro-services architecture powered by Spring Cloud, Docker, MySQL, MongoDB, Rabbitmq and more._**

This simple e-commerce application demonstrates a very simple online shopping application on BE services that provide services so that user can list out products login and make order... All the user activity should be tracked into the database without any impact to user experience...

###Functional Microservices
* **Product Microservice**
* **Order Microservice**
* **User Microservice**

###Infrastructure Microservices
* **API Gateway**
* **Config Server**

![High-level Block Diagram](https://github.com/buimaiduc/iCommerce/blob/master/infrastructure_microservices.png "High-level Overview Diagram")

The application is setup as multi-level project where each microservice is arranged as a sub-module under single parent project. It enables to run each microservice individually.


###Database design

![High-level Database Diagram](https://github.com/buimaiduc/iCommerce/blob/master/database_design.png "High-level Database Diagram")

##Prerequisites
* **_JDK 8_**
* **Maven**
* **_Docker_** -
* **_IntelliJ IDEA Community Edition [Optional]_**

##Installation
#### Clone Repository
Clone respository source code by executing following instruction to any folder on your machine,
```
git clone https://github.com/buimaiduc/iCommerce.git
cd iCommerce
```
#### Install databases
```
cd <Replace this with e-commerce-microservices folder path>/docker
./start.sh
```
###Building Application
#### Building Common Lib
```
cd <Replace this with e-commerce-microservices folder path>/shopping-common-lib
mvn install
```
#### Building Microservices(in case without any IDE support)
```
cd <Replace this with e-commerce-microservices folder path>/shopping-config-server
mvn clean install package
cd <Replace this with e-commerce-microservices folder path>/shopping-api-gateway
mvn clean install package
cd <Replace this with e-commerce-microservices folder path>/shopping-product-service
mvn clean install package
cd <Replace this with e-commerce-microservices folder path>/shopping-order-service
mvn clean install package
cd <Replace this with e-commerce-microservices folder path>/shopping-user-service
mvn clean install package
cd <Replace this with e-commerce-microservices folder path>/shopping-audit-worker
mvn clean install package
```

#### Running Microservices(in case without any IDE support)
In order to keep them easy to keep track and configure, I keep all the microservices port running and running services order as below:
Order | Service | Running Port | Command line
--- | --- | --- | --- 
`1` | shopping-config-server | 9999 | java -jar [Replace this with e-commerce-microservices folder path]/target/shopping-config-server-0.0.1-SNAPSHOT.jar |
`2` | shopping-api-gateway | 8080 | java -jar [Replace this with e-commerce-microservices folder path]/target/shopping-api-gateway-0.0.1-SNAPSHOT.jar |
`3` | shopping-product-service | 8081 | java -jar [Replace this with e-commerce-microservices folder path]/target/shopping-product-service-0.0.1-SNAPSHOT.jar |
`4` | shopping-order-service | 8082 | java -jar [Replace this with e-commerce-microservices folder path]/target/shopping-order-service-0.0.1-SNAPSHOT.jar |
`5` | shopping-user-serivce | 8083 | java -jar [Replace this with e-commerce-microservices folder path]/target/shopping-user-service-0.0.1-SNAPSHOT.jar |
`6` | shopping-user-serivce | 8084 | java -jar [Replace this with e-commerce-microservices folder path]/target/shopping-audit-worker-0.0.1-SNAPSHOT.jar |

##Product Microservice
#### Overview
Product Catalog Microservice manages e-commerce application's products. This microservice is built as Spring Boot application with MySQL as persistance store for product information and Rabbitmq as the event message source.

#### REST API(can be listed out on swagger contracts: http://localhost:8081/swagger-ui.html)
Product Microservice REST API supports following opertations:

Method | URI | Description | Parameters | Request JSON | Response JSON
--- | --- | --- | --- | --- | ---
`GET` | */v1/brands* | List of brands | None | List of brands |
`POST` | */v1/brands* | Create new brand | {"name": <string>} | Brand json |
`GET` | */v1/products?id={ids}* | List of products by ids | None | List products' json |
`POST` | */v1/products* | Create new product | {  "brandId": 0,  "colour": "string",  "image": "string",  "name": "string",  "price": 0} | Product json |
`GET` | */v1/products/{id}* | Get product by id | None |Product's json |
`PUT` | */v1/products/{id}* | Update product by id(only price for now) | {"price": 0} |Product's json |
`DELETE` | */v1/products/{id}* | Delete product by id | None | None |
`GET` | */v1/products/{id}/prices* | Get price tracking by product id | None | List price trackings json |
`GET` | */v1/products/search* | Search, filter products by criteria | Json request | List products' json |

Example Product search criteria:
```
{
  "filterFields": {
    "equalCriteria": {},
    "inCriteria": {
      "colour": [
        "red"
      ]
    },
    "likeCriteria": {
      "name": "shoe"
    },
    "notEqualCriteria": {},
    "notInCriteria": {
     
    }
  },
  "page": 0,
  "searchValue": "",
  "size": 10,
  "sortRequest": {
    "sortDirection": "ASC",
    "sortField": "price"
  }
}
```
##Order Microservice
#### Overview
Order Microservice provides e-commerce application's shopping cart functionality. This microservice is built as Spring Boot application with MySQL as persistance store for product information.

#### REST API(can be listed out on swagger contracts: http://localhost:8082/swagger-ui.html)
Order Microservice REST API supports following opertations:

Method | URI | Description | Parameters | Request JSON | Response JSON
--- | --- | --- | --- | --- | ---
`GET` | */v1/orders/{orderId}* | Get order by id | Headers: Bearer Token | Order response |
`POST` | */v2/orders* | Create order | Headers: Bearer Token, Payload: {"orderDetailRequests": [{"price": 0, "productId": 0}]} | _[TODO]_ |

##User Microservice
#### Overview
User Catalog Microservice manages e-commerce application's users. This microservice is built as Spring Boot application with MySQL as persistance store for product information.

#### REST API(can be listed out on swagger contracts: http://localhost:8083/swagger-ui.html)
User Microservice REST API supports following opertations:

Method | URI | Description | Parameters | Request JSON
--- | --- | --- | --- | ---
`POST` | */users/login* | User login with facebook access token | {"facebookToken": "string"} | { "token": "string"}
`GET` | */users/me* | Get current loggin user profile | Headers: Bearer Token | { <User JSON> }

