package com.example.androidmvvmclean.domain.usecase

import com.example.androidmvvmclean.data.model.Fund
import com.example.androidmvvmclean.data.repository.FundRepository

class GetFundSearchUseCase(
    private val fundRepository: FundRepository
) {
    suspend operator fun invoke(keyword: String): List<Fund> {
        return fundRepository.searchFunds(keyword)
    }
}
