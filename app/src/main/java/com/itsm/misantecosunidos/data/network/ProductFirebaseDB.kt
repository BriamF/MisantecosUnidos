package com.itsm.misantecosunidos.data.network

import android.util.Log
import com.itsm.misantecosunidos.core.FirebaseHelper
import com.itsm.misantecosunidos.data.model.ProductModel
import com.itsm.misantecosunidos.data.model.ProductModel.Companion.toProduct
import kotlinx.coroutines.tasks.await

class ProductFirebaseDB {
    private val firebaseDB = FirebaseHelper.getFirebaseInstance().collection("Business")
    private val TAG = "FirebaseProductsDB"

    suspend fun getAllProductsBusiness(email:String): List<ProductModel> {
        return try {
            firebaseDB.document(email).collection("Products").get().await()
                .documents.mapNotNull { it.toProduct() }
        } catch (e: Exception) {
            Log.e(TAG, "Error obteniendo productos", e)
            emptyList()
        }
    }

    fun addProduct(email:String, product: ProductModel) {
        val newProductMap = hashMapOf(
            "img" to product.img,
            "price" to product.price,
        )
        firebaseDB.document(email).collection("Products").document(product.name).set(newProductMap)
            .addOnSuccessListener {
                Log.d(TAG, "Producto agregado correctamente")
            }
            .addOnFailureListener {
                Log.e("firebase", "Error agregando un nuevo documento en la base de datos", it)
            }
    }

    fun editProduct(email: String, product: ProductModel) {
        val newProductMap = hashMapOf(
            "img" to product.img,
            "price" to product.price,
        )

        firebaseDB.document(email).collection("Products").document(product.name).set(newProductMap)
    }

    fun deleteProduct(email: String, name: String) {
        firebaseDB.document(email).collection("Products").document(name)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "Producto eliminado correctamente") }
            .addOnFailureListener { e -> Log.w(TAG, "Error en la base de datos", e) }
    }
}