package com.itsm.misantecosunidos.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.misantecosunidos.data.model.Application.Companion.prefs
import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.data.model.UserModel
import com.itsm.misantecosunidos.domain.GetBusinessIdUseCase
import com.itsm.misantecosunidos.domain.GetBusinessUseCase
import com.itsm.misantecosunidos.domain.GetUserIdUseCase
import com.itsm.misantecosunidos.domain.GetUsersUseCase
import com.itsm.misantecosunidos.ui.view.MenuBusiness
import com.itsm.misantecosunidos.ui.view.MenuUser
import com.itsm.misantecosunidos.ui.view.Register
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private lateinit var context: Context

    private var userData:UserModel? = null
    private var businessData:BusinessModel? = null

    private var usersList = MutableLiveData<List<UserModel>>()
    private var businessList = MutableLiveData<List<BusinessModel>>()

    var getUsersUseCase = GetUsersUseCase()
    var getBusinessUseCase = GetBusinessUseCase()

    fun init(context: Context) {
        this.context = context

        viewModelScope.launch {
            val resultU = getUsersUseCase()
            val resultB = getBusinessUseCase()

            if(!resultU.isNullOrEmpty() || !resultB.isNullOrEmpty()){
                usersList.postValue(resultU!!)
                businessList.postValue(resultB!!)
            }
        }

        validateDataUser()
    }

    private fun validateDataUser() {
        if (prefs.getEmail().isNotEmpty()) {
            enterSystem()
        }
    }

    private fun enterSystem() {
        if(userData != null){
            val intent = Intent(context, MenuUser::class.java)
            context.startActivity(intent)
        } else {
            val intent = Intent(context, MenuBusiness::class.java)
            context.startActivity(intent)
        }
    }

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage


    fun signIn(email:String, password:String) {
        if(validateForm(email, password)){
            if(validateUser(email, password)){
                savePreferences()
                enterSystem()
                statusMessage.value = Event("¡Bienvenido ${email}!")
            }else {
                statusMessage.value = Event("Usuario o contraseña incorrectas")
            }
        }
    }

    private fun savePreferences() {
        if(userData != null){
            prefs.saveUser(userData!!)
        } else {
            val userBusiness = UserModel(businessData!!.img, businessData!!.name, businessData!!.email, businessData!!.password)
            prefs.saveUser(userBusiness!!)
        }
    }

    private fun validateForm(email:String, password:String):Boolean = (email.isNotEmpty() && email.isNotEmpty())

    private fun validateUser(email:String, password:String) : Boolean{
        val getUserIdUseCase = GetUserIdUseCase(email)
        val user = getUserIdUseCase()

        val getBusinessIdUseCase = GetBusinessIdUseCase(email)
        val business = getBusinessIdUseCase()

        if (user != null) {
            this.userData = user!!
            return user!!.password == password
        } else if(business != null){
            this.businessData = business!!
            return business!!.password == password
        } else{
            return false
        }
    }

    fun registerMain(context : Context){
        val intent = Intent(context, Register::class.java)
        context.startActivity(intent)
    }

}
