package com.do55anto5.digitalbank.domain.auth

import com.do55anto5.digitalbank.presenter.data.repository.auth.AuthFirebaseDataSourceImpl

class RegisterUsecase(
    private val authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
) {

    suspend operator fun invoke(name: String, email: String, phone: String, password: String) {
        return authFirebaseDataSourceImpl.register(name, email, phone, password)
    }

}