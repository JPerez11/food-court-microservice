<br>
<div>
<h3 style="text-align: center">PRAGMA POWER-UP</h3>
  <p style="text-align: center">
    In this challenge, the backend of a system that centralizes the services and orders of a restaurant chain that has different branches in the city is designed. Specifically, this microservice focuses on the management of restaurants, dishes and orders.
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Spring security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
* ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Installation

###### Recommendation: Watch the following video ######
![YouTube](https://img.shields.io/badge/YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white)
<br>LINKS: 
<br> https://youtu.be/xIQZolmvbtM - Project explained
<br> https://youtu.be/PLQHsIf7g5g - Additional project settings

1. Clone the repository
   <br>
   <b>Steps:</b>
   <br>
   1. Press the green ``code`` button and press the copy button to copy the link
   <br><br>
   ![clone-repository-food-court.png](src/main/resources/vendor/img/clone-repository-food-court.png)
   <br><br>
   2. Open a terminal like Git Bash and type the command ``git clone`` + the repository link copied earlier
   ```shell
   git clone https://github.com/JPerez11/food-court-microservice
   ```
   3. Change directory
   ```shell
   cd food-court-microservice
   ```
2. Create a new database in MySQL called food_court_microservice if the schema is not created with the statement: `createDatabaseIfNotExists=true` <b>OPTIONAL STEP</b>
3. Update the database connection settings (If necessary, change the localhost port if you have another port for MySQL)
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      datasource:
          url: jdbc:mysql://localhost:3306/food_court_microservice?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true
          username: root
          password: <your-password>
          driver-class-name: com.mysql.cj.jdbc.Driver
   ```
4. Run the data.sql file to import the sample categories into the database
<br>
<b>Steps:</b>
<br>
   1. Find the "data.sql" file in the path: ``src/main/resources``
   <br><br>
   ![path-data.sql.png](src/main/resources/vendor/img/path-data.sql.png)
   <br><br>
   2. Open the ``data.sql`` file, select the statements, and run the script
   <br><br>
   ![sentence-data.sql.png](src/main/resources/vendor/img/sentence-data.sql.png)

5. After the tables are created, it is necessary to run the other microservice "user-microservice" to be able to consume the authentication token
   * Open Swagger UI in the following path: [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html), then look for the /auth/login endpoint.
   ![endpoint-auth-login.png](src/main/resources/vendor/img/endpoint-auth-login.png)
   * Enter a valid credentials for login, for example:
   ![login-as-owner.png](src/main/resources/vendor/img/login-as-owner.png)
   * Copy the token that is generated when executing the request (omit quotation marks when copying)
   ![login-token-response.png](src/main/resources/vendor/img/login-token-response.png)
   * The next step is to paste the token in the pop-up generated by the "Authorize" button of the Swagger of this microservice.
   ![paste-token-food-court.png](src/main/resources/vendor/img/paste-token-food-court.png)
6. Then you can make use of the restaurant endpoints, you should be aware that some have restrictions on the role of the user who is logging in.

<!-- USAGE -->
## Usage

1. Right-click the class FoodCourtMicroserviceApplication and choose Run
2. Open [http://localhost:8091/swagger-ui/index.html](http://localhost:8091/swagger-ui/index.html) in your web browser

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage
