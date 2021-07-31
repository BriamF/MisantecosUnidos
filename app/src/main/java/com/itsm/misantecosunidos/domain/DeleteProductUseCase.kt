package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.ProductRepository
import com.itsm.misantecosunidos.data.UserRepository

class DeleteProductUseCase(private val email:String, private val name:String) {
    private val repository = ProductRepository()

    operator fun invoke() {
        repository.deleteProduct(email, name)
    }
}