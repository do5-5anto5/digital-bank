package com.do55anto5.digitalbank.presenter.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.domain.auth.RegisterUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) :ViewModel() {

    fun register(name: String,
                 email: String,
                 phone: String,
                 password: String
    ) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val user = registerUseCase.invoke(name, email, phone, password)

            emit(StateView.Success(user))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

}