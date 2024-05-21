package com.do55anto5.digitalbank.presenter.features.transfer.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.domain.profile.GetProfileUseCase
import com.do55anto5.digitalbank.domain.transfer.GetTransferUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class TransferReceiptViewModel @Inject constructor(
    private val getTransferUseCase: GetTransferUseCase,
    private val getProfileUseCase: GetProfileUseCase
): ViewModel() {

    fun getTransfer(id: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val transfer = getTransferUseCase.invoke(id)

            emit(StateView.Success(transfer))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

    fun getProfile(id: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val user = getProfileUseCase.invoke(id)

            emit(StateView.Success(user))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}