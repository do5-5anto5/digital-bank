package com.do55anto5.digitalbank.presenter.features.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.domain.profile.GetProfilesListUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class TransferUserListViewModel @Inject constructor(
    private val getProfilesListUseCase: GetProfilesListUseCase
) : ViewModel() {

    fun getProfilesList() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val usersList = getProfilesListUseCase.invoke()

            emit(StateView.Success(usersList))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}