package com.example.androidmvvmclean.domain.usecase

import com.example.androidmvvmclean.data.model.User
import com.example.androidmvvmclean.data.repository.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Int): User? {
        return userRepository.getUserById(id)
    }
}
