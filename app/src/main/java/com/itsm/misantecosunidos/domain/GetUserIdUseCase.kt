package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.model.UserModel
import com.itsm.misantecosunidos.data.model.UserProvider

class GetUserIdUseCase(private val email: String) {
    operator fun invoke():UserModel? {
        val users = UserProvider.users
        var user: UserModel? = null
        if (!users.isNullOrEmpty()) {
            users.forEach {
                if (it.email == this.email) {
                    user = it
                }
            }
        }
        return user
    }
}