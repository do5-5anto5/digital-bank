package com.do55anto5.digitalbank.data.repository.profile

import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class ProfileDataSourceImpl @Inject constructor(
    database: FirebaseDatabase
) : ProfileDataSource {

    private val profileReference = database.reference
        .child("profile")

    override suspend fun saveProfile(user: User) {
        return suspendCoroutine { continuation ->
            profileReference
                .child(FireBaseHelper.getUserId())
                .setValue(user).addOnCompleteListener { task ->
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

    override suspend fun getProfile(): User {
        return suspendCoroutine { continuation ->
            profileReference
                .child(FireBaseHelper.getUserId())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = snapshot.getValue(User::class.java)
                    user?.let {
                        continuation.resumeWith(Result.success(it))
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWith(Result.failure(error.toException()))
                }

            })

        }
    }

    override suspend fun getProfilesList(): List<User> {
        return suspendCoroutine { continuation ->
            profileReference
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val usersList = mutableListOf<User>()

                        for (ds in snapshot.children) {
                            val user = ds.getValue(User::class.java)
                            user?.let {
                                usersList.add(it)
                            }
                        }

                        continuation.resumeWith(Result.success(
                            usersList.apply{
                                removeIf { it.id == FireBaseHelper.getUserId() }
                            }))

                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWith(Result.failure(error.toException()))
                    }

                })

        }
    }
}
