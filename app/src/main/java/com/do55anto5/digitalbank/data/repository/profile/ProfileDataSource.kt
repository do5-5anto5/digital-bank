package com.do55anto5.digitalbank.data.repository.profile

import com.do55anto5.digitalbank.data.model.User

interface ProfileDataSource {

    suspend fun saveProfile(user: User)

    suspend fun getProfile(id: String): User

    suspend fun getProfilesList(): List<User>

    suspend fun saveImage(imageProfile: String): String

}