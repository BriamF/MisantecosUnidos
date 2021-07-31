package com.itsm.misantecosunidos.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.misantecosunidos.data.model.Application.Companion.prefs
import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.data.model.BusinessProvider
import com.itsm.misantecosunidos.data.model.ProductModel
import com.itsm.misantecosunidos.data.model.ProductProvider
import com.itsm.misantecosunidos.domain.AddProductUseCase
import com.itsm.misantecosunidos.domain.GetBusinessUseCase
import com.itsm.misantecosunidos.domain.GetProductsUseCase
import com.itsm.misantecosunidos.ui.view.Login
import com.itsm.misantecosunidos.ui.view.Products
import kotlinx.coroutines.launch

class MenuUserViewModel: ViewModel() {

    fun logOut(context: Context) {
        statusMessage.value = Event("¡Hasta luego!")
        prefs.clearPreferences()
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
    }

    init {
        val getBusinessUseCase = GetBusinessUseCase()

        viewModelScope.launch {
            val result = getBusinessUseCase()

            if(!result.isNullOrEmpty()){
                businessList.postValue(result!!)
                BusinessProvider.businessList.observeForever {
                    businessList.postValue(it)
                }
            }
        }


    }

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    private var businessList = MutableLiveData<List<BusinessModel>>()
    val listBusiness: LiveData<List<BusinessModel>>
        get() = businessList

    fun showProducts(context: Context, businessModel: BusinessModel){
        val extras = Bundle();
        extras.putString("EMAIL", businessModel.email);
        extras.putString("NAME", businessModel.name);
        extras.putString("IMAGE", businessModel.img);

        val intent = Intent(context, Products::class.java)
        intent.putExtras(extras)
        context.startActivity(intent)
    }
}