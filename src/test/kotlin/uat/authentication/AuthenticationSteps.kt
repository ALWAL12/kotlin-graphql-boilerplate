package uat.authentication

import com.google.common.truth.Truth.assertThat
import io.cucumber.java8.En
import uat.AcceptanceTestRunner
import javax.ws.rs.core.Response

class AuthenticationSteps : En {

    lateinit var email: String
    lateinit var response: Response

    init {
        Given("a user provided email {string}") { email: String ->
            this.email = email
        }

        When("retrieving a user by its email") {
            response = AcceptanceTestRunner.client(endpoint = "/users")
                .queryParam("email", email)
                .request()
                .get()
        }

        Then("the user can log in") {
            assertThat(response.status).isEqualTo(200)
        }

        Then("the user cannot log in") {
            assertThat(response.status).isEqualTo(204)
        }
    }
}
