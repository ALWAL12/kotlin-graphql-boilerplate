package ca.nexapp

import ca.nexapp.monitoring.PingResource
import ca.nexapp.users.UserRepository
import ca.nexapp.users.api.UserFiller
import ca.nexapp.users.api.UserResource
import ca.nexapp.users.persistence.UserInMemoryRepository
import com.codahale.metrics.health.HealthCheck
import io.dropwizard.setup.Environment
import org.slf4j.LoggerFactory

class Application : io.dropwizard.Application<Configuration>() {

    private val logger = LoggerFactory.getLogger(Application::class.java)

    override fun run(configuration: Configuration, environment: Environment) {
        val healthCheck = object : HealthCheck() {
            override fun check(): Result {
                return Result.healthy()
            }

        }
        environment.healthChecks().register("Health Check", healthCheck)

        val userRepository: UserRepository = UserInMemoryRepository()
        val userResource = UserResource(userRepository)

        val pingResource = PingResource(version = configuration.version)

        UserFiller.fill(userRepository)

        environment.jersey().register(pingResource)
        environment.jersey().register(userResource)

        logger.warn("Application has started in {}", configuration.environment)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application().run(*args)
        }
    }

}