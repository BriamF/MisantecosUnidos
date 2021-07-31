package com.itsm.misantecosunidos.data.network

import android.util.Log
import com.itsm.misantecosunidos.core.FirebaseHelper
import com.itsm.misantecosunidos.data.model.UserModel
import com.itsm.misantecosunidos.data.model.UserModel.Companion.toUser
import kotlinx.coroutines.tasks.await

class UserFirebaseDB {

    private val firebaseDB = FirebaseHelper.getFirebaseInstance().collection("Users")
    private val TAG = "FirebaseUserDB"

    suspend fun getAllUsers(): List<UserModel> {
        return try {
            firebaseDB.get().await()
                    .documents.mapNotNull { it.toUser() }
        } catch (e: Exception) {
            Log.e(TAG, "Error obteniendo usuarios", e)
            emptyList()
        }
    }

    fun addUser(user: UserModel) {
        println("GUARDANDO USUARIO")
        val newUserMap = hashMapOf(
                "img" to user.img,
                "name" to user.name,
                "email" to user.email,
                "password" to user.password,
        )
        firebaseDB.document(user.email).set(newUserMap)
                .addOnSuccessListener {
                    Log.d(TAG, "Usuario agregado correctamente")
                }
                .addOnFailureListener {
                    Log.e("firebase", "Error agregando un nuevo documento en la base de datos", it)
                }
    }

    fun editUser(user: UserModel) {
        val newUserMap = hashMapOf(
                "img" to user.img,
                "name" to user.name,
                "email" to user.email,
                "password" to user.password,
        )

        firebaseDB.document(user.email).set(newUserMap)
    }

    fun deleteUser(email: String) {
        firebaseDB.document(email)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "Usuario eliminado correctamente") }
                .addOnFailureListener { e -> Log.w(TAG, "Error en la base de datos", e) }
    }

}