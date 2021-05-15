# restful-web-services
This repository is meant for learning and experimenting in Spring Boot and
Microservices.

GET call:
--------
curl http://localhost:8080/users -u user:52aa4e53-3b88-4278-8b5b-87cab47b44c9 | python -m json.tool

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
