package ca.nexapp.users.usecases

data class UserAlreadyRegisteredException(val email: String) : Exception(email + " already exists")