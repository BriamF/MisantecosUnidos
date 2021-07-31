package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.ProductRepository
import com.itsm.misantecosunidos.data.model.ProductModel

class GetProductsUseCase (private val email:String){
    private val repository = ProductRepository()

    suspend operator fun invoke(): List<ProductModel>? = repository.getAllProducts(email)
}