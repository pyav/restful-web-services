# restful-web-services
This repository is meant for learning and experimenting in Spring Boot and
Microservices.

## Run application
Run the Spring Boot application from RestfulWebServicesApplication.java file. This can be done from the Run-dropdown in IDE.

## GET call:
```
curl http://localhost:8080/users -u \<user\>:\<password\> | python -m json.tool
```
```json
[
    {
        "birthDate": "2021-05-15T16:25:14.953+00:00",
        "id": 1,
        "name": "pyav"
    },  
    {   
        "birthDate": "2021-05-15T16:25:14.953+00:00",
        "id": 2,
        "name": "just-another-user1"
    },  
    {   
        "birthDate": "2021-05-15T16:25:14.953+00:00",
        "id": 3,
        "name": "anand"
    }   
]
```
After disabling csrf (Cross Site Request Forgery) as given in WebSecurityConfig.java file:

```
curl http://localhost:8080/users | python -m json.tool
```
```json
[
    {
        "birthDate": "2021-05-16T08:52:32.260+00:00",
        "id": 1,
        "name": "pyav"
    },
    {
        "birthDate": "2021-05-16T08:52:32.260+00:00",
        "id": 2,
        "name": "just-another-user1"
    },
    {
        "birthDate": "2021-05-16T08:52:32.260+00:00",
        "id": 3,
        "name": "anand"
    }
]
```

## POST call:
```
curl -v  -d '{"name":"verma", "birthDate":"2021-05-16T13:34:59.485+00:00"}' http://localhost:8080/users -H 'Content-Type: application/json' -X POST
```

## Verification:
```
curl http://localhost:8080/users | python -m json.tool
```
```json
[
    {
        "birthDate": "2021-05-16T08:52:32.260+00:00",
        "id": 1,
        "name": "pyav"
    },
    {
        "birthDate": "2021-05-16T08:52:32.260+00:00",
        "id": 2,
        "name": "just-another-user1"
    },
    {
        "birthDate": "2021-05-16T08:52:32.260+00:00",
        "id": 3,
        "name": "anand"
    },
    {
        "birthDate": "2021-05-16T13:34:59.485+00:00",
        "id": 4,
        "name": "verma"
    }
]
```
## GET call: (for HATEOAS)
```
curl http://localhost:8080/users/1 | python -m json.tool
```
```json
{
    "_links": {
        "all-users": {
            "href": "http://localhost:8080/users"
        }
    },
    "birthDate": "2021-05-20T16:16:32.407+00:00",
    "id": 1,
    "name": "pyav"
}
```
## Swagger-ui
First add the dependency for springdoc-openapi in pom.xml and once it gets
downloaded, rerun the application and from the browser, call the url: http://localhost:8080/swagger-ui.html
This will redirect to index.html and you would see the swagger UI for the
endpoints that you have as part of the application.
## OpenAPI documentation
The OpenAPI documentation that gets generated and displayed on the swagger ui,
can be found in api-docs.json file. This file is created manually and the
contents can be found from swagger-ui itself by clicking on the link at
"/v3/api-docs".
## Actuator url
Following is the actuator url which can be used to monitor health of the application, resources, etc.:
http://localhost:8080/actuator
## HAL Explorer
HAL Explorer helps in exploring the APIs in a visualized and way.
The url http://localhost:8080/explorer/index.html#uri=/ redirects to http://localhost:8080/explorer/index.html#uri=/
## Versioning
Following is the command to verify one of the ways of versioning in the url:
```
curl http://localhost:8080/person/produces -H "Accept: application/v2+json" | python3 -m json.tool
```
```json
{
    "name": {
        "firstName": "Anand",
        "lastName": "pyav"
    }
}
```
### Types of versioning:
1. Media type versioning (a.k.a "content negotiation" or "accept header")  
2. (Custom) headers versioning  
3. URI versioning  
4. Request parameter versioning  

### Factors to consider for tradeoff:
1. URI Pollution  
2. Misuse of HTTP headers  
3. Caching  
4. Browser supportability  
5. Documentation  

## Basic authentication
To enable basic authentication, add the artifact "spring-boot-starter-security"
to pom.xml and comment the csrf disable code in WebSecurityConfig.java. Once
the code is built and run, the password will be printed on the build output
window. The default user is 'user'.

## H2 console (JDBC):
The url for h2 console is given below. Please also check the pom.xml for
relevant artifact.

http://localhost:8080/h2-console

NOTE: If "spring-boot-starter-security" is present in pom.xml, the sql box
won't be shown on the browser. Therefore, following line needs to be added in
the configure method of WebSecurityConfig class:

http.headers().frameOptions().disable();

The second method is to comment out the security dependency in the pom.xml
file, comment out the code in WebSecurityConfig.java file and rerun the
project.

From the sql box in the h2-console, we can run any sql command to verify the
database entry, for example, "select * from user". This will print the values
we have insert from a sql file to the table, say from data.sql file.

## JPA database
The file UserJPAResource.java is added to show the JPA APIs. Here is a sample
command and it's output to showcase the h2 in-memory database:
```
curl http://localhost:8080/jpa/users | python3 -m json.tool
```
```json
[
    {
        "id": 1,
        "name": "AB",
        "birthDate": "2021-06-12T18:30:00.000+00:00"
    },
    {
        "id": 2,
        "name": "Jill",
        "birthDate": "2021-06-12T18:30:00.000+00:00"
    },
    {
        "id": 3,
        "name": "Jack",
        "birthDate": "2021-06-12T18:30:00.000+00:00"
    }
]
```
```
curl http://localhost:8080/jpa/users/1 | python3 -m json.tool
```
```json

{
    "id": 1,
    "name": "AB",
    "birthDate": "2021-06-12T18:30:00.000+00:00",
    "_links": {
        "all-users": {
            "href": "http://localhost:8080/jpa/users"
        }
    }
}
```
## Delete call
After adding the UserRepository interface which is an extension of
JpaRepository and using it in UserJPAResource.java file, here is the delete
curl call to delete a user:
```
curl -X DELETE http://localhost:8080/jpa/users/100
curl -v  -d '{"name":"pyav", "birthDate":"2021-05-16T13:34:59.485+00:00"}' http://localhost:8080/jpa/users -H 'Content-Type: application/json' -X POST
```
### Validation for the delete
```
curl http://localhost:8080/jpa/users | python3 -m json.tool
```
```json
[
    {
        "id": 2,
        "name": "pyav",
        "birthDate": "2021-05-16T13:34:59.485+00:00"
    },
    {
        "id": 3,
        "name": "pyav",
        "birthDate": "2021-05-16T13:34:59.485+00:00"
    },
    {
        "id": 200,
        "name": "Jill",
        "birthDate": "2021-06-22T18:30:00.000+00:00"
    },
    {
        "id": 300,
        "name": "Jack",
        "birthDate": "2021-06-22T18:30:00.000+00:00"
    }
]
```
## POST call
Here is a demo for POST call which creates a post for a user:
```
curl http://localhost:8080/jpa/users/200/posts -XPOST -d'{ "description": "My Post New" }' -H 'Content-Type: application/json'
```
The verification can be done from h2 console by using the following command:
```
select * from POST where user_id='200'
```
## Richardson Maturity Model
### Level 0:
Exposing SOAP web services in REST style. For example, ```http://<server>/getPosts```
### Level 1:
Expose resources with proper URI but improper user of HTTP methods.
### Level 2:
Level 1 + proper user of HTTP methods.
### Level 3:
Level 2 + HATEOAS i.e. Data + Next possible actions
## Challenges with Microservices
1. Bounded Context - not easy to define boundaries initially and may keep getting changed
2. Configuration Management
3. Dynamic scale up and scale down - load distribution
4. Visibility - how to identify where a bug is, monitoring, etc.
5. Pack of Cards - design well, have fault tolerance
