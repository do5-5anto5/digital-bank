package com.do55anto5.digitalbank.data.repository.profile

import com.do55anto5.digitalbank.data.model.User

interface ProfileDataSource {

    suspend fun saveProfile(user: User)

    suspend fun getProfile(): User

    suspend fun getProfilesList(): List<User>

}