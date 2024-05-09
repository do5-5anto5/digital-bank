package com.do55anto5.digitalbank.domain.profile

import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.data.repository.profile.ProfileDataSourceImpl
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val profileDataSourceImpl: ProfileDataSourceImpl
){

    suspend operator fun invoke(user: User) {
        return profileDataSourceImpl.saveProfile(user)
    }

}