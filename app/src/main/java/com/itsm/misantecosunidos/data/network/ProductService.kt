package com.itsm.misantecosunidos.data.network

import com.itsm.misantecosunidos.data.model.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductService {
    private val firebaseDB = ProductFirebaseDB()

    suspend fun getProducts(email: String):List<ProductModel> {
        return withContext(Dispatchers.IO) {
            val response = firebaseDB.getAllProductsBusiness(email)
            response
        }
    }

    fun addProduct(email: String, product: ProductModel) {
        firebaseDB.addProduct(email, product)
    }

    fun editProduct(email: String, product: ProductModel) {
        firebaseDB.editProduct(email, product)
    }

    fun deleteProduct(email: String, name: String) {
        firebaseDB.deleteProduct(email, name)
    }
}