package ca.nexapp

import ca.nexapp.monitoring.PingResource
import ca.nexapp.users.UserRepository
import ca.nexapp.users.api.UserFiller
import ca.nexapp.users.api.UserResource
import ca.nexapp.users.persistence.UserInMemoryRepository
import com.codahale.metrics.health.HealthCheck
import com.expedia.graphql.SchemaGeneratorConfig
import com.expedia.graphql.TopLevelObject
import com.expedia.graphql.toSchema
import com.smoketurner.dropwizard.graphql.GraphQLBundle
import com.smoketurner.dropwizard.graphql.GraphQLFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.eclipse.jetty.servlets.CrossOriginFilter
import org.slf4j.LoggerFactory
import java.util.*
import javax.servlet.DispatcherType


class Application : io.dropwizard.Application<Configuration>() {

    private val logger = LoggerFactory.getLogger(Application::class.java)

    override fun initialize(bootstrap: Bootstrap<Configuration>) {
        super.initialize(bootstrap)

        val graphQLBundle = object : GraphQLBundle<Configuration>() {
            override fun getGraphQLFactory(configuration: Configuration): GraphQLFactory {

                val config = SchemaGeneratorConfig(supportedPackages = listOf("ca.nexapp"))
                val queries = listOf(TopLevelObject(UserResource::class))
                val mutations = emptyList<TopLevelObject>()
                var schema = toSchema(config, queries, mutations)

                val factory = GraphQLFactory()
                factory.setGraphQLSchema(schema)
                return factory
            }
        }
        bootstrap.addBundle(graphQLBundle)
    }

    override

    fun run(configuration: Configuration, environment: Environment) {
        val healthCheck = object : HealthCheck() {
            override fun check(): Result {
                return Result.healthy()
            }

        }
        environment.healthChecks().register("Health Check", healthCheck)

        val cors = environment.servlets().addFilter("cors", CrossOriginFilter::class.java)
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")

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