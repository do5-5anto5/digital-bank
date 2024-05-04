package com.do55anto5.digitalbank.data.repository.auth

import com.do55anto5.digitalbank.data.model.User

interface AuthFirebaseDataSource {

    suspend fun login(email: String, password: String)
    suspend fun register(
        user: User
    ): User
    suspend fun recover(email: String)
}