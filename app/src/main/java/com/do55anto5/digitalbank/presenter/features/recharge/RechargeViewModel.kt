package com.do55anto5.digitalbank.presenter.features.recharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.data.model.Recharge
import com.do55anto5.digitalbank.data.model.Transaction
import com.do55anto5.digitalbank.domain.recharge.SaveRechargeUseCase
import com.do55anto5.digitalbank.domain.transaction.GetBalanceUseCase
import com.do55anto5.digitalbank.domain.transaction.SaveTransactionUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RechargeViewModel @Inject constructor(
    private val saveRechargeUseCase: SaveRechargeUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase,
    private val getBalanceUseCase: GetBalanceUseCase
) : ViewModel() {

    fun getBalance() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val balance = getBalanceUseCase.invoke()

            emit(StateView.Success(balance))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

    fun saveRecharge(recharge: Recharge) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val result = saveRechargeUseCase.invoke(recharge)

            saveRechargeUseCase.invoke(recharge)

            emit(StateView.Success(result))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

    fun saveTransaction(transaction: Transaction) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            saveTransactionUseCase.invoke(transaction)

            emit(StateView.Success(Unit))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}