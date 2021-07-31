package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.UserRepository
import com.itsm.misantecosunidos.data.model.UserModel

class DeleteUserUseCase (private val id: String) {
    private val repository = UserRepository()

    operator fun invoke() {
        repository.deleteUser(id)
    }
}