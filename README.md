# Spring Boot "REST API" integration with RabbitMQ

This is a sample Java / Maven / Spring Boot application that can be used as a starter for creating a microservice complete with built-in health check, metrics and much more features.

## How to Run 

This application is developed using spring boot - 1.5.2.RELEASE and packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.8 and Maven 4.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test target/spring-boot-restapi-demo-0.0.1-SNAPSHOT.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```

Once the application runs you should see something like this

```
* 2017-03-07 23:21:36.175  INFO 22388 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9999 (http)
* 2017-03-07 23:21:36.188  INFO 22388 --- [           main] com.tilindra.demo.Application            : Started Application in 16.624 seconds (JVM running for 17.576)
```

## About the Service

The service is just a simple employee REST service. It uses MySQL relational database for accessing data and for JUnit, in-memory database has been used. If your database connection properties work, you can call some REST endpoints defined in ```com.tilindra.demo.api.rest.employeeController``` on **port 9999**. (see below)


You can use this sample service to understand the conventions and configurations that allow you to create a DB-backed RESTful service. Once you understand and get comfortable with the sample app you can add your own services following the same patterns as the sample service.
 
Here is what this little application demonstrates: 

* Full integration with the latest **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single war with embedded container (tomcat 8): No need to install a container separately on the host just run using the ``java -jar`` command
* Demonstrates how to set up healthcheck, metrics, info, environment, etc. endpoints automatically on a configured port. Inject your own health / metrics info with a few lines of code.
* Writing a RESTful service using annotation: supports both XML and JSON request / response; simply use desired ``Accept`` header in your request
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations. 
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* Demonstrates MockMVC test framework with associated libraries
* All APIs are "self-documented" by Swagger using annotations 

Here are some endpoints you can call:

### Get information about system health, configurations, etc.

```
* http://localhost:9999/env
* http://localhost:9999/health
* http://localhost:9999/info
* http://localhost:9999/metrics
```

### Create an employee resource

```
POST /demo/createEmployee
Accept: application/json
Content-Type: application/json

{
"name" : "Michael",
"description" : "Dancer",
"city" : "Dublin",
}

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:9999/demo/createEmployee/1
```

### Update a employee resource

```
PUT /demo/employee/1
Accept: application/json
Content-Type: application/json

{
"name" : "Michael Jackson",
"description" : "Break Dancer",
"city" : "Dublin",
}

RESPONSE: HTTP 204 (No Content)
```

# About Spring Boot

Spring Boot is an "opinionated" application bootstrapping framework that makes it easy to create new RESTful services (among other types of applications). It provides many of the usual Spring facilities that can be configured easily usually without any XML. In addition to easy set up of Spring Controllers, Spring Data, etc. Spring Boot comes with the Actuator module that gives the application the following endpoints helpful in monitoring and operating the service:

**/metrics** Shows “metrics” information for the current application.

**/health** Shows application health information.

**/info** Displays arbitrary application info.

**/configprops** Displays a collated list of all @ConfigurationProperties.

**/mappings** Displays a collated list of all @RequestMapping paths.

**/beans** Displays a complete list of all the Spring Beans in your application.

**/env** Exposes properties from Spring’s ConfigurableEnvironment.

**/trace** Displays trace information (by default the last few HTTP requests).


### Running the application using the 'mysql' profile:

```
        * java -jar -Dspring.profiles.active=mysql target/spring-boot-restapi-demo-0.0.1-SNAPSHOT.war
or
        * mvn spring-boot:run -Drun.arguments="spring.profiles.active=mysql"
```

### Swagger API Documentation

The Swagger API Documentation can be found by at the below URL: http://localhost:9999/swagger-ui.html#/ and below are the snapshot of endpoints that can be run using Swagger.

* audit-events-mvc-endpoint : Audit Events Mvc Endpoint
* employee-controller : Employee Controller
* endpoint-mvc-adapter : Endpoint Mvc Adapter
* environment-mvc-endpoint : Environment Mvc Endpoint
* health-mvc-endpoint : Health Mvc Endpoint
* heapdump-mvc-endpoint : Heapdump Mvc Endpoint
* loggers-mvc-endpoint : Loggers Mvc Endpoint
* metrics-mvc-endpoint : Metrics Mvc Endpoint

### ERLANG Installation

1. Download and install latest OTP.exe from http://www.erlang.org/downloads
2. Set ERLANG_HOME = <<erl installation path>>
Example: C:\Program Files\erl8.2 into the System Variables

### RabbitMQ Installation

1. Download and install from the page: http://www.rabbitmq.com/install-windows.html

### RabbitMQ Web Management Plugin installation

1.  Open an elevated command line (Run as Administrator)

2. Navigate to the sbin directory of the RabbitMQ Server installation directory. In my case the path is 

C:\Program Files (x86)\RabbitMQ Server\rabbitmq_server-3.3.4\sbin

3. Run the following command to enable the plugin

 rabbitmq-plugins.bat enable rabbitmq_management
 
4. Then, re-install the RabbitMQ service using the commands below:

rabbitmq-service.bat stop  
rabbitmq-service.bat install  
rabbitmq-service.bat start  

To check if everything worked as expected, navigate to http://localhost:15672/mgmt. You will be prompted for username and password. The default credentials are guest/guest

###Integrating RabbitMQ to Application

1. RabbitMQ configuration are managed at application.yml using below properties:

```
rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtualHost: / 
```

2. Create a RabbitMQ bean configuration to create exchange/queues at the startup of application

3. Use Producer/Publisher to convertAndSend the POJO to RabbitMQ

4. Use Consumer to get the published message in the RabbitMQ 

# Questions and Comments: tejilindra@gmail.com




