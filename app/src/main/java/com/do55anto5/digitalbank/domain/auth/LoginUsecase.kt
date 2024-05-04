package com.do55anto5.digitalbank.domain.auth

import com.do55anto5.digitalbank.data.repository.auth.AuthFirebaseDataSourceImpl
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
) {

    suspend operator fun invoke(email: String, password: String) {
        return authFirebaseDataSourceImpl.login(email, password)
    }

}