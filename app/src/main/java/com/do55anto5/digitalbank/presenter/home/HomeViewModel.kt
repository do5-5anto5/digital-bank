package com.do55anto5.digitalbank.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.domain.transaction.GetTransactionsUseCase
import com.do55anto5.digitalbank.domain.wallet.GetWalletUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    fun getWallet() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val wallet = getWalletUseCase.invoke()

            emit(StateView.Success(wallet))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

    fun getTransactions() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val transactions = getTransactionsUseCase.invoke()

            emit(StateView.Success(transactions))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

}