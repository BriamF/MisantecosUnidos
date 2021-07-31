package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.data.model.BusinessProvider

class GetBusinessIdUseCase(private val email:String) {
    operator fun invoke(): BusinessModel? {
        val allBusiness = BusinessProvider.business
        var business: BusinessModel? = null
        if (!allBusiness.isNullOrEmpty()) {
            allBusiness.forEach {
                if (it.email == this.email) {
                    business = it
                }
            }
        }
        return business
    }
}