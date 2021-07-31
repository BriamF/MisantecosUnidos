package com.itsm.misantecosunidos.data

import com.itsm.misantecosunidos.data.model.ProductModel
import com.itsm.misantecosunidos.data.model.ProductProvider
import com.itsm.misantecosunidos.data.network.ProductService

class ProductRepository {

    private val service = ProductService()

    suspend fun getAllProducts(email:String):List<ProductModel> {
        val response = service.getProducts(email)
        ProductProvider.products = response as MutableList<ProductModel>
        return response
    }

    fun addProduct(email: String, product: ProductModel) {
        val products = ProductProvider.products
        products.add(product)
        ProductProvider.productsList.postValue(products)
        return service.addProduct(email, product)
    }

    fun editProduct(email: String, product: ProductModel) {
        val products = ProductProvider.products
        service.editProduct(email, product)
        products.forEachIndexed { index, productModel ->
            if (productModel.name == product.name){
                ProductProvider.products[index] = product
            }
        }
        ProductProvider.productsList.postValue(products)
    }

    fun deleteProduct(email: String, name: String) {
        val products = ProductProvider.products
        service.deleteProduct(email, name)
        products.removeIf { it.name == name }
        ProductProvider.productsList.postValue(products)
    }
}