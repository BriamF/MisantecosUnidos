package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.UserRepository
import com.itsm.misantecosunidos.data.model.UserModel

class EditUserUseCase(private val user: UserModel) {
    private val repository = UserRepository()

    operator fun invoke() {
        repository.editUser(user)
    }
}