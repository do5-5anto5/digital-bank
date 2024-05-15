package com.do55anto5.digitalbank.presenter.features.recharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.domain.recharge.GetRechargeUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RechargeReceiptViewModel @Inject constructor(
private val getRechargeUseCase: GetRechargeUseCase
) : ViewModel(){

    fun getRecharge(id: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val result = getRechargeUseCase.invoke(id)

            emit(StateView.Success(result))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}