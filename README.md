# Backend Demo

Read it in [日本語](https://github.com/lifeodyssey/demo/blob/gxp/README-JP.md)

This code repository is a demonstration of basic CRUD and user authentication in backend microservice architecture based on Kotlin and Spring

![gxp 1107.png](gxp%201107.png)

## How to run it locally?

Please make sure you have installed docker and gradle in your local machine. Then please clone this branch and open it in terminal

```bash
git switch mensetsu
chmod 755 ./BuildAndDeployToLocal.sh
./BuildAndDeployToLocal.sh
```
docker will build the images and run services locally, including book service, bff service and a MongoDB

You can open the ./http/rest-api.http and run it with local env to test it (Please make sure you are using idea Ultimate Edition and have installed [IDEA HTTP Client plugin]( https://plugins.jetbrains.com/plugin/13121-http-client))

![test result.png](test%20result.png)

Although this repository is mainly written in Kotlin, it should be readable for Javaer(Acutually the newest Java grammer is closing to Kotlin, like it has auto type detection)

## Highlight of this demo

- Basic architecture of microservice
  - Backend for Frontend(BFF): combine response from different single service, handle technical problems like auth, circuit breaker,rate limiter, redirection,etc
  - Authentication and Authorization: setup of basic auth use spring security with different permissions for different api
  - Single Responsibility: it achieved this principle by separate different microservices and BFF layer.
- Network Isolation: the BFF layer is open to public, while book service and MongoDB are running in private network. The only interface for book service is BFF.
- Hook, Lint , Test Coverage and Swagger plugin: the githook is set up under /.githooks. One enabled, the code could not be pushed to remote repository when it could not pass test coverage check, test and lint check. The swagger ui is available at http://localhost:8000/swagger-ui/index.html, which could help frontend and upstream services to debug 
- Test: Unit test, Application test and Integration test are implemented based on Junit, Mockk and WireMock
- Database migration: it use mongock to do the Database migration when changing the schema of data, like flyway for PostgreSQL.

## Drawback of this demo
- The auth service is not really tested besides the HTTP script, and the auth service is not really implemented as a single service
- There are some code smells currently, for example
  - The PUT request should use map as request body
  - The test should extract fixture as they are using same data
  - There are no CI/CD pipeline setup in this repo. Currently,use 'BuildAndDeploToLocal.sh' instead
  - The total test coverages are only around 80% and the branch specified test coverages are only about 50%. This is extremely low in bff.
  - The apiTest is not implemented, and the integration test are not fully implemented
  - the config for in-memory mongodb (de.flapdoodle.mongodb.embedded.version) is showed in bff layer, which obviously should not appear here.
- The infra related config（like MongoDB, docker compose file） are all written in this repository, which should be in another repo use Infra-as-Code way
