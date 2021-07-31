package com.itsm.misantecosunidos.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsm.misantecosunidos.R
import com.itsm.misantecosunidos.data.model.Application.Companion.prefs
import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.databinding.ActivityMenuBusinessBinding
import com.itsm.misantecosunidos.databinding.ActivityMenuUserBinding
import com.itsm.misantecosunidos.ui.viewmodel.MenuBusinessViewModel
import com.itsm.misantecosunidos.ui.viewmodel.MenuUserViewModel
import com.itsm.misantecosunidos.ui.viewmodel.adapters.BusinessAdapter
import com.itsm.misantecosunidos.ui.viewmodel.adapters.ProductAdapter

class MenuUser : AppCompatActivity(), BusinessAdapter.IUItems{
    private lateinit var binding: ActivityMenuUserBinding
    private val menuUserViewModel: MenuUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initRecyclerView()
    }

    private fun initUI() {
        menuUserViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.btnLogOutU.setOnClickListener {
            menuUserViewModel.logOut(this)
        }
    }

    private fun initRecyclerView() {
        val adapter = BusinessAdapter(this,this)
        binding.businessData.layoutManager = LinearLayoutManager(this)
        binding.businessData.adapter = adapter
        menuUserViewModel.listBusiness.observe(this, Observer {
            adapter.setListaDatos(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun showProducts(itemView: View, business: BusinessModel) {
        menuUserViewModel.showProducts(itemView.context, business)
    }
}