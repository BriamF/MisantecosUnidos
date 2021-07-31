package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.UserRepository
import com.itsm.misantecosunidos.data.model.UserModel

class AddUserUseCase(private val user: UserModel) {
    private val repository = UserRepository()

    operator fun invoke() {
        repository.addUser(user)
    }
}