# restful-web-services
This repository is meant for learning and experimenting in Spring Boot and
Microservices.

GET call:
--------
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
After disabling csrf () as given in WebSecurityConfig.java file:

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

POST call:
---------
curl -v  -d '{"name":"verma", "birthDate":"2021-05-16T13:34:59.485+00:00"}' http://localhost:8080/users -H 'Content-Type: application/json' -X POST
.
< HTTP/1.1 200 
.

Verification:
------------
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

