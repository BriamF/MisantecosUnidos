package com.itsm.misantecosunidos.data.model

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProductModel(
    var img:String = "URL DE LA IMAGEN DEL PRODUCTO",
    val name:String = "NOMBRE DEL PRODUCTO",
    val price:Float = 0f,
): Parcelable {

    companion object {
        fun DocumentSnapshot.toProduct(): ProductModel? {
            try {
                val img = getString("img")!!
                val price = getLong("price")?.toFloat()!!
                return ProductModel(img, id, price)
            } catch (e: Exception) {
                Log.e(TAG, "Error convirtiendo el modelo del producto", e)
                return null
            }
        }
        private const val TAG = "Business"
    }
}