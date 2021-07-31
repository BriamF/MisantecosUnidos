package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.BusinessRepository
import com.itsm.misantecosunidos.data.model.BusinessModel

class GetBusinessUseCase {
    private val repository = BusinessRepository()

    suspend operator fun invoke(): List<BusinessModel>? = repository.getAllBusiness()
}