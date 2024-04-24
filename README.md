# Case Study - Parking Lot

<p align="center">
    <img src="screenshots/main_image.png" alt="Main Information" width="700" height="500">
</p>

### 📖 Information

<ul style="list-style-type:disc">
  <li><b>Parking Lot</b> is a kind of Spring Boot with covering important and useful features</li> 
  <li>Here is the explanation of the example
       <ul>There are 2 roles named <b>Admin</b> and <b>Driver</b></ul>
       <ul>Explanation A</ul>
  </li>
</ul>

### Explore Rest APIs

<table style="width:100%">
  <tr>
      <th>Method</th>
      <th>Url</th>
      <th>Description</th>
      <th>Request Body</th>
      <th>Header</th>
      <th>Valid Path Variable</th>
      <th>No Path Variable</th>
  </tr>
</table>



### Technologies

---
- Java 17
- Spring Boot 3.0
- Spring Security
- JWT
- Restful API
- Lombok
- Maven
- Junit5
- Mockito
- Integration Tests
- Docker
- Docker Compose
- CI/CD (Github Actions)
- Prometheus and Grafana
- Postman
- Actuator
- Swagger 3


### Swagger

```
http://localhost:1222/swagger-ui/index.html
```

### Prerequisites

#### Define Variable in .env file

```
DATABASE_USERNAME={DATABASE_USERNAME}
DATABASE_PASSWORD={DATABASE_PASSWORD}
PARKING_LOT_LIQUIBASE_ENABLE_DROP_FIRST=true
```

---
- Maven or Docker
---


### Docker Run
The application can be built and run by the `Docker` engine. The `Dockerfile` has multistage build, so you do not need to build and run separately.

Please follow directions shown below in order to build and run the application with Docker Compose file;

```sh
$ cd parkinglot
$ docker-compose up -d
```

If you change anything in the project and run it on Docker, you can also use this command shown below

```sh
$ cd parkinglot
$ docker-compose up --build
```

---
### Maven Run
To build and run the application with `Maven`, please follow the directions shown below;

```sh
$ cd parkinglot
$ mvn clean install
$ mvn spring-boot:run
```

### Screenshots

<details>
<summary>Click here to show the screenshots of project</summary>
    <p> Figure 1 </p>
    <img src ="screenshots/1.PNG">
</details>

### Contributors

- [Sercan Noyan Germiyanoğlu](https://github.com/Rapter1990)
- [Muhammet Oğuzhan Aydoğdu](https://github.com/moaydogdu)
- [Mehmet Şeymus Yüzen](https://github.com/mehmetseymusyuzen)
- [Harun Yusuf Ekşioğlu](https://github.com/artfulCoder98)
