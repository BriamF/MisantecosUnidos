package com.itsm.misantecosunidos.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.itsm.misantecosunidos.ui.view.Login
import com.itsm.misantecosunidos.ui.view.Register
import com.itsm.misantecosunidos.ui.view.RegisterBusiness
import com.itsm.misantecosunidos.ui.view.RegisterUser

class RegisterViewModel: ViewModel() {
    fun registerUser(context : Context){
        val intent = Intent(context, RegisterUser::class.java)
        context.startActivity(intent)
    }

    fun registerBussiness(context : Context){
        val intent = Intent(context, RegisterBusiness::class.java)
        context.startActivity(intent)
    }

    fun backLogin(context : Context){
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
    }
}