import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("Integration-test")
@AutoConfigureWireMock(port = 0, files = ["classpath:/stubs"])
abstract class BaseIntegrationTest