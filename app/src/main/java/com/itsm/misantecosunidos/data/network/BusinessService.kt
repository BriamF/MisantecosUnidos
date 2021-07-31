package com.itsm.misantecosunidos.data.network

import com.itsm.misantecosunidos.data.model.BusinessModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BusinessService {
    private val firebaseDB = BusinessFirebaseDB()

    suspend fun getBusiness():List<BusinessModel> {
        return withContext(Dispatchers.IO) {
            val response = firebaseDB.getAllBusiness()
            response
        }
    }

    fun addBusiness(business: BusinessModel) {
        firebaseDB.addBusiness(business)
    }

    fun editBusiness(business: BusinessModel) {
        firebaseDB.editBusiness(business)
    }

    fun deleteBusiness(email: String) {
        firebaseDB.deleteBusiness(email)
    }
}