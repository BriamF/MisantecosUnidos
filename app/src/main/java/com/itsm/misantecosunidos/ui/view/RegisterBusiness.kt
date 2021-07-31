package com.itsm.misantecosunidos.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.data.model.UserModel
import com.itsm.misantecosunidos.databinding.ActivityRegisterBusinessBinding
import com.itsm.misantecosunidos.databinding.ActivityRegisterUserBinding
import com.itsm.misantecosunidos.ui.viewmodel.RegisterBusinessViewModel
import com.itsm.misantecosunidos.ui.viewmodel.RegisterUserViewModel

class RegisterBusiness : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBusinessBinding
    private  val registerBusinessViewModel: RegisterBusinessViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.btnBackB.setOnClickListener {
            registerBusinessViewModel.registerMain(this)
        }

        registerBusinessViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.btnRegisterBusiness.setOnClickListener {
            val newBusiness = BusinessModel("", binding.inpName.text.toString(), binding.inpEmail.text.toString(), binding.inpPass.text.toString(), binding.inpAddress.text.toString(), binding.inpPhoneNumber.text.toString())
            registerBusinessViewModel.registerNewBusiness(newBusiness)
            cleanFields()
        }

        registerBusinessViewModel.textImage.observe(this, Observer {
            binding.btnUploadImage.text = it
        })

        binding.btnUploadImage.setOnClickListener { registerBusinessViewModel.subirImagen(this)}

    }
    private fun cleanFields() {
        binding.inpEmail.setText("")
        binding.inpName.setText("")
        binding.inpPass.setText("")
        binding.inpAddress.setText("")
        binding.inpPhoneNumber.setText("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        registerBusinessViewModel.onActivityResult(requestCode, resultCode, data)
    }
}