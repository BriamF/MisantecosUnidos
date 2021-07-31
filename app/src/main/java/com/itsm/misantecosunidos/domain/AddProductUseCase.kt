package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.ProductRepository
import com.itsm.misantecosunidos.data.model.ProductModel

class AddProductUseCase (private val email: String, private val product: ProductModel) {
    private val repository = ProductRepository()

    operator fun invoke() {
        repository.addProduct(email, product)
    }
}