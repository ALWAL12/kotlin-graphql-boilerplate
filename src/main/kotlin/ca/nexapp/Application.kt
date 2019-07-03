package ca.nexapp

import ca.nexapp.monitoring.PingResource
import com.codahale.metrics.health.HealthCheck
import io.dropwizard.setup.Environment

class Application : io.dropwizard.Application<Configuration>() {

    override fun run(configuration: Configuration, environment: Environment) {
        val healthCheck = object : HealthCheck() {
            override fun check(): Result {
                return Result.healthy()
            }

        }
        environment.healthChecks().register("Health Check", healthCheck)

        val pingResource = PingResource(version = configuration.version)
        environment.jersey().register(pingResource)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application().run(*args)
        }
    }

}