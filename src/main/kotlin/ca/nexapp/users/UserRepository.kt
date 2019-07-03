package ca.nexapp.users

interface UserRepository {

    fun add(user: User)

    fun findByEmail(email: String): User?
}