package com.do55anto5.digitalbank.presenter.features.transfer.confirm_transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.domain.transaction.GetBalanceUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ConfirmTransferViewModel @Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase
): ViewModel() {

    fun getBalance() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val balance = getBalanceUseCase.invoke()

            emit(StateView.Success(balance))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}