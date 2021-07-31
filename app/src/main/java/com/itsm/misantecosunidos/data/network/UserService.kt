package com.itsm.misantecosunidos.data.network

import com.itsm.misantecosunidos.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val firebaseDB = UserFirebaseDB()

    suspend fun getUsers():List<UserModel> {
        return withContext(Dispatchers.IO) {
            val response = firebaseDB.getAllUsers()
            response
        }
    }

    fun addUser(user: UserModel) {
        firebaseDB.addUser(user)
    }

    fun editUser(user: UserModel) {
        firebaseDB.editUser(user)
    }

    fun deleteUser(email: String) {
        firebaseDB.deleteUser(email)
    }

}