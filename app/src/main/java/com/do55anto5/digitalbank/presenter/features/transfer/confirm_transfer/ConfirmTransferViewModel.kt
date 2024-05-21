package com.do55anto5.digitalbank.presenter.features.transfer.confirm_transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.data.model.Transfer
import com.do55anto5.digitalbank.domain.transaction.GetBalanceUseCase
import com.do55anto5.digitalbank.domain.transfer.SaveTransferUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ConfirmTransferViewModel @Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val saveTransferUseCase: SaveTransferUseCase
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

    fun saveTransfer(transfer: Transfer) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            saveTransferUseCase.invoke(transfer)

            emit(StateView.Success(Unit))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}