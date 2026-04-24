package com.example.androidmvvmclean.domain.usecase

import com.example.androidmvvmclean.data.model.Fund
import com.example.androidmvvmclean.data.repository.FundRepository

class GetFundListUseCase(
    private val fundRepository: FundRepository
) {
    suspend operator fun invoke(page: Int = 1, size: Int = 20): List<Fund> {
        return fundRepository.getFundList(page, size)
    }
}
