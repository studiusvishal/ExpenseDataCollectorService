# Getting Started

## Running locally
* Install postgresql server on local
* Install pgadmin GUI tool to connect to postgresql server
* Create new user to access postgresql server

## Running from command line
```
mvn spring-boot:run \
-Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"

mvn spring-boot:run \
-Dspring-boot.run.jvmArguments="-DMAIL_APP_PASSWORD=tzzixtccfieolmwd"
```

* Install postman windows app
* Register new user - 
  * http://localhost:8080/users/signup
  * Request Type: POST
  * Header: Content-Type = application/json
  * Body:
    ```
    {
        "username": "admin",
        "password": "admin",
        "id": 1
    }
    ```
  * AuthToken will be present in the headers of response. Use it in the header of further requests.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Json Web Token: How to Secure a Spring Boot REST API](https://dzone.com/articles/json-web-token-how-to-secure-spring-boot-rest-api)
* [Spring Boot Security + JWT Hello World Example](https://medium.com/swlh/spring-boot-security-jwt-hello-world-example-b479e457664c)

## Used in this service
* [Spring login security example used in this service](https://www.bezkoder.com/spring-boot-jwt-authentication/)
* [Custom error messages in REST API](https://www.baeldung.com/global-error-handler-in-a-spring-rest-api)
