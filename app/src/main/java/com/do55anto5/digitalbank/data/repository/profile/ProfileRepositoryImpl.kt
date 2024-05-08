package com.do55anto5.digitalbank.data.repository.profile

import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl @Inject constructor(
    database: FirebaseDatabase
) : ProfileRepository {

    private val profilePreferences = database.reference
        .child("profile")
        .child(FireBaseHelper.getUserId())

    override suspend fun saveProfile(user: User) {
        return suspendCoroutine { continuation ->
            profilePreferences.setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resumeWith(Result.success(Unit))
                } else {
                    task.exception?.let {
                        continuation.resumeWith(Result.failure(it))
                    }
                }
            }
        }
    }
}