# Maybank Test

This is a Spring Boot application for the Maybank Test project.

## Getting Started

Follow the steps below to set up and run the application.

### Prerequisites

-   Java Development Kit (JDK) 8 or higher
-   Maven
-   MySQL database
-   Jasper
-   Lombok 

### Configuration

1.  Clone the repository:

`git clone https://github.com/aspyasa/maybankTest.git` 

2.  Open the project in your preferred IDE.
    
3.  Configure the following properties in the `application.properties` file located in the `src/main/resources` directory:


`# GitHub API token (replace with your own token)

    api.token=YOUR_GITHUB_API_TOKEN

# MySQL database connection settings

    spring.datasource.url=jdbc:mysql://localhost:3306/db_report?createDatabaseIfNotExist=true
    spring.datasource.username=YOUR_DB_USERNAME
    spring.datasource.password=YOUR_DB_PASSWORD` 


### Running the Application

To run the application, follow these steps:

1.  Open a terminal or command prompt.
    
2.  Navigate to the project directory.
    
3.  Build the application using Maven:
    
    Copy code
    
    `mvn clean install` 
    
4.  Run the application:
    
    `mvn spring-boot:run` 
    
    The application will start running on the configured port (default is 8080).
    

### Importing Postman Collection

To import the Postman collection and test the API endpoints, follow these steps:

1.  Open Postman.
    
2.  Click on "Import" in the top-left corner.
    
3.  Select the "Import From Link" tab.
    
4.  Enter the following link and click "Continue":
    
`https://raw.githubusercontent.com/aspyasa/maybankTest/master/Maybank%20Technical%20test.postman_collection.json` 

    
5.  The collection will be imported into Postman. You can now explore the available API endpoints and send requests.
    

### Folder Structure and Purpose

The folder structure of the Maybank Test application is as follows:

-   `src/main/java`: Contains the Java source code for the application.
    -   `com.maybank.testproject.controller`: Contains the REST API controller classes.
    -   `com.maybank.testproject.dto`: Contains the DTO (Data Transfer Object) classes used for request and response payloads.
    -   `com.maybank.testproject.models`: Contains the entity classes representing database tables.
    -   `com.maybank.testproject.exception`: Contains the exception classes used for custom error handling.
    -   `com.maybank.testproject.repository`: Contains the repository interfaces for database operations.
    -   `com.maybank.testproject.service`: Contains the service interfaces and implementations.
    -   `com.maybank.testproject.util`: Contains utility classes and constants.
    -   `com.maybank.testproject.configuration`: Contains classes responsible for configuring various components and settings within the application.
-   `src/main/resources`: Contains the application configuration files.
-   `src/test/java`: Contains the unit tests for the application.
-   `Maybank Technical test.postman_collection.json`: Contains the Postman collection for testing the API endpoints.




## Design Patterns

The following design patterns are utilized in this project:

### 1. Model-View-Controller (MVC) Pattern

The application follows the Model-View-Controller (MVC) architectural pattern. It separates the application into three main components:

-   **Model**: Represents the data and business logic. The models in this project are the entities and DTOs (Data Transfer Objects) used for data representation and transfer.
    
-   **View**: Handles the presentation layer and user interface. In this project, the views are not explicitly implemented as the application primarily serves as an API.
    
-   **Controller**: Manages the interaction between the model and the view. The controllers in this project handle the HTTP requests, perform business logic, and return appropriate responses.
    

### 2. Repository Pattern

The Repository Pattern is used for managing the persistence operations on entities in the database. The repositories in this project are responsible for accessing and manipulating data in the database. They provide an abstraction layer between the application and the underlying data source.

### 3. Data Transfer Object (DTO) Pattern

The Data Transfer Object (DTO) pattern is employed to transfer data between different layers of the application. DTOs are used to encapsulate data and transport it across the application boundaries. In this project, DTOs are used to send and receive data between the controller and service layers.

### 4. Dependency Injection (DI)

The Dependency Injection (DI) pattern is utilized for managing dependencies between components in the application. DI helps decouple the dependencies and allows for easier testing, flexibility, and maintainability. In this project, DI is implemented using the `@Autowired` annotation to inject dependencies into classes.



    

### Running Unit Tests

To run the unit tests for the application, execute the following command:


`mvn test` 


## Additional Notes

-   The Jasper templates can be found in the `src/main/resources/templates` directory. You can add or modify the templates in this folder.

----------

Please make sure to replace the placeholders `YOUR_GITHUB_API_TOKEN`, `YOUR_DB_USERNAME`, and `YOUR_DB_PASSWORD` with your own values in the `application.properties` file.
