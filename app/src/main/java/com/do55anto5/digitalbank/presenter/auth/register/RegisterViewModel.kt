package com.do55anto5.digitalbank.presenter.auth.register

import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.domain.auth.RegisterUsecase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUsecase: RegisterUsecase
) {

    fun register(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            registerUsecase.invoke(user)

            emit(StateView.Success(user))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

}