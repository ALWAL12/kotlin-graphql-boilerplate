package ca.nexapp.users.usecases

import ca.nexapp.users.User
import ca.nexapp.users.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class UserRegistrationUseCaseTest {

    private val userRepository: UserRepository = mockk(relaxed = true)

    private val userRegistration = UserRegistrationUseCase(userRepository)

    @Test(expected = UserAlreadyRegisteredException::class)
    fun givenAnAlreadyExistingEmail_ShouldThrowAnException() {
        val user = User(email = "john.doe@email.com", name = "John Doe")
        every { userRepository.findByEmail("john.doe@email.com") } returns user

        userRegistration.register(user)
    }

    @Test
    fun givenANonExistingEmail_ShouldAddTheUser() {
        val user = User(email = "john.doe@email.com", name = "John Doe")
        every { userRepository.findByEmail("john.doe@email.com") } returns null

        userRegistration.register(user)

        verify { userRepository.add(user) }
    }
}