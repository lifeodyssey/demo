# Backend Demo
This code repository is a demonstration of basic work and best practices for backend development. The project uses the following tech stack:

- Kotlin
- Spring Boot
- MongoDB
- Mongock
- Mockk
- Docker
- Jenkins
- Nginx
- Spring Cloud

The project follows the practices of Test-Driven Development (TDD) and Trunk-Based Development. The architecture of the project is based on microservices and backend-for-frontend.

The project includes the following features:

- Jenkins CI/CD pipeline and blue/green deployment mocked by Nginx
- Basic CRUD RESTFULL API with TDD
- MongoDB migration using Mongock
- Exception handling
- Logging
## Jenkins CI/CD Pipeline and Mocked Blue/Green Deployment by Nginx
This project demonstrates a continuous integration and continuous deployment (CI/CD) pipeline using Jenkins and Nginx. The application is able to connect to a different database, defined in the application.properties file. The pipeline is configured to automatically build, test, and deploy a sample application to a local machine. To keep the application always available, Nginx is used to perform blue/green deployment.

Please note that in a real production scenario, infrastructure platforms usually have functionality to implement blue/green deployment, such as [AWS](https://d1.awsstatic.com/whitepapers/AWS_Blue_Green_Deployments.pdf). Additionally, Containers or Kubernetes clusters have commands like [`aws ecs update-service --service my-http-service --task-definition amazon-ecs-sample`](https://docs.aws.amazon.com/cli/latest/reference/ecs/update-service.html) to achieve this. Therefore, the solution presented in this project may not be suitable for use in a real production environment.

## Basic CRUD RESTFULL API with TDD
The sample application in this project is a basic CRUD RESTFULL API that allows users to create, read, update, and delete (CRUD) items in a database. The API is built using a test-driven development (TDD) approach with an embedded MongoDB.

The tests include:

- Unit Tests: using mock and stub for the service layer and object mapper
- Integration Tests: using `@WebMvcTest` for the controller layer and using mongoTemplate for the repository layer
- End-to-End Tests: using `@SpringBootTest` for the entire application, along with a smoke test
## MongoDB Migration Using Mongock 
MongoDB does not require the design of a schema like that of a relational database management system (RDBMS). However, if you want to add a new non-null field to an object, you need to use Mongock to perform the migration. Otherwise, if the new field does not exist in your MongoDB and you query data and save it to a new object, there will be a null-pointer exception for the new non-null field.

Mongock uses mongoTemplate to query data without transferring it to an object. To disable Mongock during testing, you need to set mongock.enabled=false in the application.properties file.