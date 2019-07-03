package uat

import ca.nexapp.Application
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.testing.ResourceHelpers.resourceFilePath
import io.dropwizard.testing.junit.DropwizardAppRule
import io.dropwizard.util.Duration
import org.junit.ClassRule
import org.junit.runner.RunWith
import java.util.*
import javax.ws.rs.client.WebTarget

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features"],
    tags = ["not @ignored"]
)
class AcceptanceTestRunner {

    companion object {
        @ClassRule
        @JvmField
        val RULE = DropwizardAppRule(Application::class.java, resourceFilePath("uat_config.yml"))

        fun client(endpoint: String): WebTarget {
            val configuration = JerseyClientConfiguration()
            configuration.timeout = Duration.seconds(5)

            val actualEndpoint = String.format("http://localhost:%d%s", RULE.localPort, endpoint)
            return JerseyClientBuilder(RULE.environment)
                .using(configuration)
                .build("Testing client ${UUID.randomUUID()}")
                .target(actualEndpoint)
        }
    }

}