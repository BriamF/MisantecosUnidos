package com.itsm.misantecosunidos.data

import com.itsm.misantecosunidos.data.model.UserModel
import com.itsm.misantecosunidos.data.model.UserProvider
import com.itsm.misantecosunidos.data.network.UserService

class UserRepository {

    private val service = UserService()

    suspend fun getAllUsers():List<UserModel> {
        val response = service.getUsers()
        UserProvider.users = response as MutableList<UserModel>
        return response
    }

    fun addUser(user: UserModel) {
        UserProvider.users.add(user)
        service.addUser(user)
    }

    fun editUser(user: UserModel) {
        service.editUser(user)
        UserProvider.users.forEachIndexed { index, userModel ->
            if (userModel.email == user.email){
                UserProvider.users[index] = user
            }
        }
    }

    fun deleteUser(email: String) {
        service.deleteUser(email)
        UserProvider.users.removeIf { it.email == email }
    }

}