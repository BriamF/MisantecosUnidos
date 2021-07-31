package com.itsm.misantecosunidos.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.misantecosunidos.data.model.Application
import com.itsm.misantecosunidos.data.model.ProductModel
import com.itsm.misantecosunidos.data.model.ProductProvider
import com.itsm.misantecosunidos.domain.GetProductsUseCase
import com.itsm.misantecosunidos.ui.view.MenuUser
import com.itsm.misantecosunidos.ui.view.Products
import kotlinx.coroutines.launch

class ProductsViewModel: ViewModel() {

    fun data(email: String) {
        val getProductsUseCase = GetProductsUseCase(email)

        viewModelScope.launch {
            val result = getProductsUseCase()

            if(!result.isNullOrEmpty()){
                productList.postValue(result!!)
                ProductProvider.productsList.observeForever {
                    productList.postValue(it)
                }
            }
        }
    }

    fun getBack(context: Context){
        val intent = Intent(context, MenuUser::class.java)
        context.startActivity(intent)
    }

    private var productList = MutableLiveData<List<ProductModel>>()
    val listProduct: LiveData<List<ProductModel>>
        get() = productList
}