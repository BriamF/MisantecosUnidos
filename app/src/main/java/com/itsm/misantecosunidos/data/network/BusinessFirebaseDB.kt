package com.itsm.misantecosunidos.data.network

import android.util.Log
import com.itsm.misantecosunidos.core.FirebaseHelper
import com.itsm.misantecosunidos.data.model.BusinessModel
import com.itsm.misantecosunidos.data.model.BusinessModel.Companion.toBusiness
import kotlinx.coroutines.tasks.await

class BusinessFirebaseDB {
    private val firebaseDB = FirebaseHelper.getFirebaseInstance().collection("Business")
    private val TAG = "FirebaseBusinessDB"

    suspend fun getAllBusiness(): List<BusinessModel> {
        return try {
            firebaseDB.get().await()
                .documents.mapNotNull { it.toBusiness() }
        } catch (e: Exception) {
            Log.e(TAG, "Error obteniendo negocios", e)
            emptyList()
        }
    }

    fun addBusiness(business: BusinessModel) {
        val newBusinessMap = hashMapOf(
            "img" to business.img,
            "name" to business.name,
            "password" to business.password,
            "address" to business.address,
            "phoneNumber" to business.phoneNumber
        )
        firebaseDB.document(business.email).set(newBusinessMap)
            .addOnSuccessListener {
                Log.d(TAG, "Negocio agregado correctamente")
            }
            .addOnFailureListener {
                Log.e("firebase", "Error agregando un nuevo documento en la base de datos", it)
            }
    }

    fun editBusiness(business: BusinessModel) {
        val newBusinessMap = hashMapOf(
            "img" to business.img,
            "name" to business.name,
            "password" to business.password,
            "address" to business.address,
            "phoneNumber" to business.phoneNumber
        )

        firebaseDB.document(business.email).set(newBusinessMap)
    }

    fun deleteBusiness(email: String) {
        firebaseDB.document(email)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "Negocio eliminado correctamente") }
            .addOnFailureListener { e -> Log.w(TAG, "Error en la base de datos", e) }
    }

}