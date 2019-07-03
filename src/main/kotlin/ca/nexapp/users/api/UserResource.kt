package ca.nexapp.users.api

import ca.nexapp.users.User
import ca.nexapp.users.UserRepository
import com.codahale.metrics.annotation.Timed
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
class UserResource(private val userRepository: UserRepository) {

    @GET
    @Timed
    fun findUserById(@QueryParam("email") email: String): User? {
        return userRepository.findByEmail(email)
    }
}