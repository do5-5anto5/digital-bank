package com.do55anto5.digitalbank.domain.auth

import com.do55anto5.digitalbank.presenter.data.repository.auth.AuthFirebaseDataSourceImpl

class RecoverUsecase(
    private val authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
) {

    suspend operator fun invoke(email: String) {
        return authFirebaseDataSourceImpl.recover(email)
    }

}