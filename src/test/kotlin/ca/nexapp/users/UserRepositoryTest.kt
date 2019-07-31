package ca.nexapp.users

import com.google.common.truth.Truth.assertThat
import org.junit.Test

abstract class UserRepositoryTest {

    private val userRepository: UserRepository = createUserRepository()

    @Test
    fun canRetrieveAUserByItsEmail() {
        val user = User(email = "john.doe@acme.com", name = "John Doe")
        userRepository.add(user)

        val userFound = userRepository.findByEmail("john.doe@acme.com")

        assertThat(userFound).isEqualTo(user)
    }

    @Test
    fun whenRetrievingAUserByItsEmail_ShouldBeCaseInsensitive() {
        val user = User(email = "john.doe@acme.com", name = "John Doe")
        userRepository.add(user)

        val userFound = userRepository.findByEmail("JOHN.doe@ACME.com")

        assertThat(userFound).isEqualTo(user)
    }

    @Test
    fun givenNoMatchingEmail_WhenFindingAUserByItsEmail_ItFindsNothing() {
        val userFound = userRepository.findByEmail("non.existing@email.com")

        assertThat(userFound).isNull()
    }

    abstract fun createUserRepository(): UserRepository
}