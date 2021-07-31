package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.BusinessRepository
import com.itsm.misantecosunidos.data.model.BusinessModel

class AddBusinessUseCase(private val business: BusinessModel) {
    private val repository = BusinessRepository()

    operator fun invoke() {
        repository.addBusiness(business)
    }
}