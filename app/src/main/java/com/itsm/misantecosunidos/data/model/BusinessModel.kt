package com.itsm.misantecosunidos.data.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusinessModel (
    var img:String = "URL DE LA IMAGEN DEL NEGOCIO",
    val name:String = "NOMBRE DEL NEGOCIO",
    val email:String = "EJEMPLO@DOMINIO.COM",
    val password:String = "*****",
    val address:String = "DIRECCIÓN DEL NEGOCIO",
    val phoneNumber:String = "0000000000"
): Parcelable {

    companion object {
        fun DocumentSnapshot.toBusiness(): BusinessModel? {
            try {
                val img = getString("img")!!
                val name = getString("name")!!
                val password = getString("password")!!
                val address = getString("address")!!
                val phoneNumber = getString("phoneNumber")!!
                return BusinessModel(img, name, id, password, address, phoneNumber)
            } catch (e: Exception) {
                Log.e(TAG, "Error convirtiendo el modelo del negocio", e)
                return null
            }
        }
        private const val TAG = "Business"
    }
}