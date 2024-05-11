package com.do55anto5.digitalbank.presenter.features.deposit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.data.model.Deposit
import com.do55anto5.digitalbank.domain.deposit.SaveDepositUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DepositViewModel @Inject constructor(
    private val saveDepositUseCase: SaveDepositUseCase
) : ViewModel(){
    fun saveDeposit(deposit: Deposit) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val idDeposit = saveDepositUseCase.invoke(deposit)

            saveDepositUseCase.invoke(deposit)

            emit(StateView.Success(idDeposit))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}