package com.do55anto5.digitalbank.domain.profile

import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.data.repository.profile.ProfileDataSourceImpl
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileDataSourceImpl: ProfileDataSourceImpl
){

    suspend operator fun invoke(id : String) : User {
        return profileDataSourceImpl.getProfile(id)
    }

}