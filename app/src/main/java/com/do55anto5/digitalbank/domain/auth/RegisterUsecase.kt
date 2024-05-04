package com.do55anto5.digitalbank.domain.auth

import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.data.repository.auth.AuthFirebaseDataSourceImpl
import javax.inject.Inject

class RegisterUsecase @Inject constructor(
    private val authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
) {

    suspend operator fun invoke(user: User) : User {
        return authFirebaseDataSourceImpl.register(user)
    }

}