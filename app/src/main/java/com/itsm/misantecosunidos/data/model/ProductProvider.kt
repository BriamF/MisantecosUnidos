package com.itsm.misantecosunidos.data.model

import androidx.lifecycle.MutableLiveData


class ProductProvider {
    companion object {
        var products= mutableListOf<ProductModel>()
        var productsList = MutableLiveData<List<ProductModel>>()
    }
}
