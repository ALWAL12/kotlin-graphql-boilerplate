package ca.nexapp.users.persistence

import ca.nexapp.users.UserRepository
import ca.nexapp.users.UserRepositoryTest

class UserInMemoryRepositoryTest : UserRepositoryTest() {

    override fun createUserRepository(): UserRepository {
        return UserInMemoryRepository()
    }

}