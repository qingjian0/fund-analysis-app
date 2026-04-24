package com.example.androidmvvmclean.data.repository.impl

import com.example.androidmvvmclean.data.model.User
import com.example.androidmvvmclean.data.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    override suspend fun getUsers(): List<User> {
        // Simulate network call
        return listOf(
            User(1, "John Doe", "john.doe@example.com"),
            User(2, "Jane Smith", "jane.smith@example.com"),
            User(3, "Bob Johnson", "bob.johnson@example.com")
        )
    }

    override suspend fun getUserById(id: Int): User? {
        // Simulate network call
        return getUsers().find { it.id == id }
    }
}
