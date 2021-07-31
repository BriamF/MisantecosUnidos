package com.itsm.misantecosunidos.data

import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.data.model.BusinessProvider
import com.itsm.misantecosunidos.data.model.ProductProvider
import com.itsm.misantecosunidos.data.network.BusinessService

class BusinessRepository {
    private val service = BusinessService()

    suspend fun getAllBusiness():List<BusinessModel> {
        val response = service.getBusiness()
        BusinessProvider.business = response as MutableList<BusinessModel>
        return response
    }

    fun addBusiness(business: BusinessModel) {
        val listBusiness = BusinessProvider.business
        listBusiness.add(business)
        BusinessProvider.businessList.postValue(listBusiness)
        return service.addBusiness(business)
    }

    fun editBusiness(business: BusinessModel) {
        val listBusiness = BusinessProvider.business
        service.editBusiness(business)
        listBusiness.forEachIndexed { index, businessModel ->
            if (businessModel.email == business.email){
                BusinessProvider.business[index] = business
            }
        }
        BusinessProvider.businessList.postValue(listBusiness)
    }

    fun deleteBusiness(email: String) {
        val listBusiness = BusinessProvider.business
        service.deleteBusiness(email)
        listBusiness.removeIf { it.email == email }
        BusinessProvider.businessList.postValue(listBusiness)
    }
}