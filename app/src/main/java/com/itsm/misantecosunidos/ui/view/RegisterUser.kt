package com.itsm.misantecosunidos.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.itsm.misantecosunidos.data.model.UserModel
import com.itsm.misantecosunidos.databinding.ActivityRegisterBinding
import com.itsm.misantecosunidos.databinding.ActivityRegisterUserBinding
import com.itsm.misantecosunidos.ui.viewmodel.RegisterUserViewModel
import com.itsm.misantecosunidos.ui.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register_user.*

class RegisterUser : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    private  val registerUserViewModel: RegisterUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.btnBackU.setOnClickListener {
            registerUserViewModel.registerMain(this)
        }

        registerUserViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.btnRegisterUser.setOnClickListener {
            val newUser = UserModel("", binding.inpName.text.toString(), binding.inpEmail.text.toString(), binding.inpPass.text.toString())
            registerUserViewModel.registerNewUser(newUser)
            cleanFields()
        }
        registerUserViewModel.textImage.observe(this, Observer {
            binding.btnUploadImage.text = it
        })

        binding.btnUploadImage.setOnClickListener { registerUserViewModel.subirImagen(this)}
    }

    private fun cleanFields() {
        binding.inpEmail.setText("")
        binding.inpName.setText("")
        binding.inpPass.setText("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        registerUserViewModel.onActivityResult(requestCode, resultCode, data)
    }
}