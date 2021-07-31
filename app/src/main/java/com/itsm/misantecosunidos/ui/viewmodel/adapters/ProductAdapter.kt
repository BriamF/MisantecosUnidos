package com.itsm.misantecosunidos.ui.viewmodel.adapters

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itsm.misantecosunidos.R
import com.itsm.misantecosunidos.data.model.ProductModel
import com.itsm.misantecosunidos.databinding.ProductItemBinding
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter (private val context: Context,  private val listener: IUButtons? = null, private val flag: Boolean) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var productList = listOf<ProductModel>()

    fun setListaDatos(datos:List<ProductModel>) {
        productList = datos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.product_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bindView(product)
    }

    override fun getItemCount(): Int {
        return if(productList.size >0) productList.size else 0
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ProductItemBinding.bind(itemView)
        private lateinit var productModel: ProductModel

        fun bindView(product: ProductModel) {
            this.productModel = product
            Glide.with(context).load(product.img).into(itemView.civProduct)
            binding.tvName.text = "${product.name}"
            binding.tvPrice.text = "$ ${product.price.toString()}"
            if(flag) {
                initButtons()
            }

        }

        private fun initButtons() {
            binding.btnDelete.visibility = View.VISIBLE
            binding.btnEdit.setOnClickListener {
                listener!!.editProduct(itemView,productModel)
            }
            binding.btnDelete.setOnClickListener {
                listener!!.deleteProduct(itemView,productModel.name)
            }
        }

    }
    interface IUButtons {
        fun editProduct(itemView: View, productModel: ProductModel)
        fun deleteProduct(itemView: View, nameProduct: String)
    }
}