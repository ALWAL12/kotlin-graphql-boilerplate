package ca.nexapp.users.persistence

import ca.nexapp.users.User
import ca.nexapp.users.UserRepository

class UserInMemoryRepository : UserRepository {

    var users: MutableList<User> = mutableListOf()

    override fun findByEmail(email: String): User? {
        return users.firstOrNull { user -> user.email.equals(other = email, ignoreCase = true) }
    }

    override fun add(user: User) {
        users.add(user)
    }

}