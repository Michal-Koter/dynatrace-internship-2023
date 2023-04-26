# It's my solution via Java of recruitment back-end task from Dynatrace (gda-internship-2023)

## Installation
**The application should be running on port :8080.**

Build Spring Boot project with Maven.
```
mvn package
```
 Run the application.
```
mvn spring-boot:run
```
Run tests.
 ```
mvn test
```

## Endpoints

### Average exchange rate.

Example query:
```
curl http://localhost:8080/api/rates/average/usd/2022-04-25
```
Expected result for the above query:
 ```
{
    "code": "usd",
    "average": 4.3188,
    "effective_date": "2022-04-25"
}
```

### Max and min average value
Example query:
```
curl http://localhost:8080/api/rates/average/min-max/usd/25
```
Expected result for the above query on 2023-04-26:
 ```
 {
    "min": {
        "code": "usd",
        "average": 4.1649,
        "effective_date": "2023-04-25"
    },
    "max": {
        "code": "usd",
        "average": 4.3742,
        "effective_date": "2023-03-24"
    }
}
```

### Major difference between the buy and ask rate
Example query:
```
curl http://localhost:8080/api/rates/difference/max/chf/50
```
Expected result for the above query on 2023-04-26:
 ```
{
    "code": "chf",
    "bid": 4.6198,
    "ask": 4.7132,
    "difference": -0.0934,
    "effective_date": "2023-03-02"
}
```


## Project is created with:

* JAVA 17
* Spring Boot
* Maven 3.9.1
* JUnit
* Mockito
