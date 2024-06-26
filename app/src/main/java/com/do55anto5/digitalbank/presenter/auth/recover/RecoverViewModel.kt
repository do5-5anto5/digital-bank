package com.do55anto5.digitalbank.presenter.auth.recover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.domain.auth.RecoverUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RecoverViewModel @Inject constructor(
    private val recoverUseCase: RecoverUseCase
) : ViewModel() {

    fun recover(email: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            recoverUseCase.invoke(email)

            emit(StateView.Success(null))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

}