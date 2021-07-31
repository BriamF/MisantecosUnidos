package com.itsm.misantecosunidos.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.itsm.misantecosunidos.data.model.Application.Companion.prefs
import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.data.model.ProductModel
import com.itsm.misantecosunidos.databinding.ActivityMenuBusinessBinding
import com.itsm.misantecosunidos.ui.viewmodel.MenuBusinessViewModel
import com.itsm.misantecosunidos.ui.viewmodel.adapters.ProductAdapter

class MenuBusiness : AppCompatActivity(), ProductAdapter.IUButtons {
    private lateinit var binding: ActivityMenuBusinessBinding
    private val menuBusinessViewModel: MenuBusinessViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        menuBusinessViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.btnLogOutB.setOnClickListener {
            menuBusinessViewModel.logOut(this)
        }

        binding.btnSaveProduct.setOnClickListener {
            val newProduct = ProductModel("", binding.inpName.text.toString(), binding.inpPrice.text.toString().toFloat())
            menuBusinessViewModel.saveNewProduct(newProduct)
            cleanFields()
        }

        menuBusinessViewModel.textImage.observe(this, Observer {
            binding.btnUploadImage.text = it
        })

        binding.btnUploadImage.setOnClickListener { menuBusinessViewModel.subirImagen(this)}

        putBusinessData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = ProductAdapter(this,this, true)
        binding.productData.layoutManager = LinearLayoutManager(this)
        binding.productData.adapter = adapter
        menuBusinessViewModel.listProduct.observe(this, Observer {
            adapter.setListaDatos(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun cleanFields() {
        binding.inpPrice.setText("")
        binding.inpName.setText("")
    }

    private fun putBusinessData() {
        val img = binding.ivLogo
        Glide.with(this).load(prefs.getImage()).into(img)
        binding.inpNameBusiness.text = prefs.getName()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        menuBusinessViewModel.onActivityResult(requestCode, resultCode, data)
    }

    override fun editProduct(itemView: View, productModel: ProductModel) {

    }

    override fun deleteProduct(itemView: View, nameProduct: String) {
        menuBusinessViewModel.deleteProduct(itemView.context, nameProduct)
    }
}