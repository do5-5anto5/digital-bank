package com.do55anto5.digitalbank.domain.profile

import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.data.repository.profile.ProfileRepositoryImpl
import javax.inject.Inject

class SaveProfileUsecase @Inject constructor(
    private val profileRepository: ProfileRepositoryImpl
){

    suspend operator fun invoke(user: User) {
        return profileRepository.saveProfile(user)
    }

}