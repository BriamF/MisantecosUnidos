package com.itsm.misantecosunidos.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.itsm.misantecosunidos.data.model.Application
import com.itsm.misantecosunidos.databinding.ActivityProductsBinding
import com.itsm.misantecosunidos.ui.viewmodel.ProductsViewModel
import com.itsm.misantecosunidos.ui.viewmodel.adapters.ProductAdapter

class Products : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private val productsViewModel: ProductsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = ProductAdapter(this,null, false)
        binding.productsData.layoutManager = LinearLayoutManager(this)
        binding.productsData.adapter = adapter
        productsViewModel.listProduct.observe(this, Observer {
            adapter.setListaDatos(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun initUI() {
        productsViewModel.data(intent.extras!!.getString("EMAIL")!!)

        binding.btnBack.setOnClickListener {
            productsViewModel.getBack(this)
        }

        putBusinessData(intent.extras!!.getString("IMAGE")!!, intent.extras!!.getString("NAME")!!)
    }

    private fun putBusinessData(imgUrl:String, name:String) {
        val img = binding.ivLogo
        Glide.with(this).load(imgUrl).into(img)
        binding.inpNameBusiness.text = name
    }
}