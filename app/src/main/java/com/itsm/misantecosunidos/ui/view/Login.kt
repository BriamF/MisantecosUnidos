package com.itsm.misantecosunidos.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.itsm.misantecosunidos.R
import com.itsm.misantecosunidos.databinding.ActivityLoginBinding
import com.itsm.misantecosunidos.ui.viewmodel.LoginViewModel

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MisantecosUnidos)
        Thread.sleep(2000)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initUI()
    }

    private fun initUI(){
        loginViewModel.init(this)

        loginViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.btnSignIn.setOnClickListener {
            loginViewModel.signIn(binding.inpEmail.text.toString(), binding.inpPassword.text.toString())
        }

        binding.tvRegister.setOnClickListener {
            loginViewModel.registerMain(this)
        }
    }

}