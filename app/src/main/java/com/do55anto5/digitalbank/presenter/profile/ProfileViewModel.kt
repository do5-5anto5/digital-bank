package com.do55anto5.digitalbank.presenter.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.domain.profile.GetProfileUseCase
import com.do55anto5.digitalbank.domain.profile.SaveImageProfileUseCase
import com.do55anto5.digitalbank.domain.profile.SaveProfileUseCase
import com.do55anto5.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val saveProfileUseCase: SaveProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val saveImageProfileUseCase: SaveImageProfileUseCase
) : ViewModel() {

    fun saveProfile(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            saveProfileUseCase.invoke(user)

            emit(StateView.Success(null))

        } catch (e: Exception) {
            emit(StateView.Error(e.message))
        }
    }

    fun saveImageProfile(imageProfile: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val imageUrl = saveImageProfileUseCase.invoke(imageProfile)

            emit(StateView.Success(imageUrl))

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