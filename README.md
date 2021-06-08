# restful-web-services
This repository is meant for learning and experimenting in Spring Boot and
Microservices.

## GET call:
curl http://localhost:8080/users -u \<user\>:\<password\> | python -m json.tool
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

curl http://localhost:8080/users | python -m json.tool
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
curl -v  -d '{"name":"verma", "birthDate":"2021-05-16T13:34:59.485+00:00"}' http://localhost:8080/users -H 'Content-Type: application/json' -X POST

## Verification:
curl http://localhost:8080/users | python -m json.tool
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
curl http://localhost:8080/users/1 | python -m json.tool
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
curl http://localhost:8080/person/produces -H "Accept: application/v2+json" | python3 -m json.tool
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
<br />
Factors:  
1. URI Pollution  
2. Misuse of HTTP headers  
3. Caching  
4. Browser supportability  
5. Documentation  
