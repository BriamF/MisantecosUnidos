package com.itsm.misantecosunidos.data.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel (
    var img:String = "URL DE LA IMAGEN DEL USUARIO",
    val name:String = "NOMBRE DEL USUARIO",
    val email:String = "EJEMPLO@DOMINIO.COM",
    val password:String = "*****"
) : Parcelable {

    companion object {
        fun DocumentSnapshot.toUser(): UserModel? {
            try {
                val img = getString("img")!!
                val name = getString("name")!!
                val password = getString("password")!!
                return UserModel(img, name, id, password)
            } catch (e: Exception) {
                Log.e(TAG, "Error convirtiendo el modelo del usuario", e)
                return null
            }
        }
        private const val TAG = "User"
    }
}