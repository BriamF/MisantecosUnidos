package com.itsm.misantecosunidos.ui.viewmodel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itsm.misantecosunidos.R
import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.data.model.ProductModel
import com.itsm.misantecosunidos.databinding.BusinessItemBinding
import kotlinx.android.synthetic.main.business_item.view.*

class BusinessAdapter (private val context: Context, private val listener: BusinessAdapter.IUItems) : RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>() {

    private var businessList = listOf<BusinessModel>()

    fun setListaDatos(datos:List<BusinessModel>) {
        businessList = datos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessAdapter.BusinessViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BusinessViewHolder(layoutInflater.inflate(R.layout.business_item, parent, false))
    }

    override fun onBindViewHolder(holder: BusinessAdapter.BusinessViewHolder, position: Int) {
        val business = businessList[position]
        holder.bindView(business)
    }

    override fun getItemCount(): Int {
        return if(businessList.size >0) businessList.size else 0
    }

    inner class BusinessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = BusinessItemBinding.bind(itemView)
        private lateinit var businessModel: BusinessModel

        fun bindView(business: BusinessModel) {
            this.businessModel = business
            Glide.with(context).load(business.img).into(itemView.civBusiness)
            binding.tvName.text = "${business.name}"
            binding.tvPhone.text = "$ ${business.phoneNumber.toString()}"
            binding.tvAddress.text = "${business.address}"

            initEvent()
        }

        private fun initEvent(){
            binding.itemBusiness.setOnClickListener {
                listener.showProducts(itemView, businessModel)
            }
        }
    }

    interface IUItems {
        fun showProducts(itemView: View, business: BusinessModel)
    }
}