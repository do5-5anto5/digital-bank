package com.do55anto5.digitalbank.data.repository.profile

import com.do55anto5.digitalbank.data.model.User

interface ProfileRepository {

    suspend fun saveProfile (user: User)

}