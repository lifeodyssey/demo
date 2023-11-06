package com.example.bff

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ActiveProfiles("integration-test")
@AutoConfigureWireMock(port = 0, files = ["classpath:/stubs"])
@SpringBootTest(
    webEnvironment = RANDOM_PORT
)
abstract class BaseIntegrationTest