package com.example.androidmvvmclean.domain.usecase

import com.example.androidmvvmclean.data.model.User
import com.example.androidmvvmclean.data.repository.UserRepository

class GetUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): List<User> {
        return userRepository.getUsers()
    }
}
