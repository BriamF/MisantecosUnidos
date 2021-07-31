package com.itsm.misantecosunidos.domain

import com.itsm.misantecosunidos.data.UserRepository
import com.itsm.misantecosunidos.data.model.UserModel

class GetUsersUseCase {
    private val repository = UserRepository()

    suspend operator fun invoke(): List<UserModel>? = repository.getAllUsers()
}