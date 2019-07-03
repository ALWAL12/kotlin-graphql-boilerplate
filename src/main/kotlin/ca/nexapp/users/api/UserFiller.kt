package ca.nexapp.users.api

import ca.nexapp.users.User
import ca.nexapp.users.UserRepository

object UserFiller {

    fun fill(userRepository: UserRepository) {
        val user = User(email = "john.doe@acme.com", name = "John Doe")

        userRepository.add(user)
    }
}