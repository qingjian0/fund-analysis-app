package com.example.androidmvvmclean.data.repository

import com.example.androidmvvmclean.data.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: Int): User?
}
