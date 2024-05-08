package com.do55anto5.digitalbank.presenter.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.domain.profile.SaveProfileUsecase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val saveProfileUsecase: SaveProfileUsecase
) : ViewModel() {

    fun saveProfile(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            saveProfileUsecase.invoke(user)

            emit(StateView.Success(null))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }
}