package com.itsm.misantecosunidos.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.itsm.misantecosunidos.databinding.ActivityRegisterBinding
import com.itsm.misantecosunidos.ui.viewmodel.RegisterViewModel

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private  val registerViewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterUser.setOnClickListener { registerViewModel.registerUser(this) }

        binding.btnRegisterBusiness.setOnClickListener { registerViewModel.registerBussiness(this) }

        binding.btnBackLogin.setOnClickListener { registerViewModel.backLogin(this) }
    }
}