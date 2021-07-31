package com.itsm.misantecosunidos.data.model

import android.content.Context

class UserPrefs(private val context: Context) {

    val SHARED_USER_IMG = "Image URL"
    val SHARED_USER_NAME = "Name"
    val SHARED_USER_EMAIL = "Email"
    val SHARED_USER_PASSWORD = "Password"

    val storage = context.getSharedPreferences(SHARED_USER_NAME, 0)

    fun saveUser(user: UserModel) {
        storage.edit()
                .putString(SHARED_USER_IMG,user.img)
                .putString(SHARED_USER_NAME,user.name)
                .putString(SHARED_USER_EMAIL,user.email)
                .putString(SHARED_USER_PASSWORD,user.password)
                .apply()
    }

    fun saveImg(imgUrl: String){
        storage.edit().putString(SHARED_USER_IMG, imgUrl).apply()
    }

    fun saveName(name: String){
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }

    fun saveEmail(email: String){
        storage.edit().putString(SHARED_USER_EMAIL, email).apply()
    }

    fun savePassword(password: String){
        storage.edit().putString(SHARED_USER_PASSWORD, password).apply()
    }

    fun getName():String{
        return storage.getString(SHARED_USER_NAME, "")!!
    }

    fun getImage():String{
        return storage.getString(SHARED_USER_IMG, "")!!
    }

    fun getEmail():String{
        return storage.getString(SHARED_USER_EMAIL, "")!!
    }

    fun getPassword():String{
        return storage.getString(SHARED_USER_PASSWORD, "")!!
    }

    fun clearPreferences() {
        storage.edit().clear().apply()
    }

}