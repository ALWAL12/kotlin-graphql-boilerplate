package ca.nexapp.users.usecases

import ca.nexapp.users.User
import ca.nexapp.users.UserRepository

class UserRegistrationUseCase(private val userRepository: UserRepository) {

    fun register(user: User) {
        val exists = userRepository.findByEmail(user.email) !== null
        if (exists) {
            throw UserAlreadyRegisteredException(user.email)
        }
        userRepository.add(user)
    }
}