package com.itsm.misantecosunidos.data.model

import androidx.lifecycle.MutableLiveData

class BusinessProvider {
    companion object {
        var business= mutableListOf<BusinessModel>()
        var businessList = MutableLiveData<List<BusinessModel>>()
    }
}