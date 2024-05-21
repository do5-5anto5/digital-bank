package com.do55anto5.digitalbank.data.repository.profile

import android.net.Uri
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class ProfileDataSourceImpl @Inject constructor(
    database: FirebaseDatabase,
    storage: FirebaseStorage
) : ProfileDataSource {

    private val databaseReference = database.reference
        .child("profile")

    private val storageReference = storage.reference
        .child("images")
        .child("profiles")
        .child("${FireBaseHelper.getUserId()}.jpeg")

    override suspend fun saveProfile(user: User) {
        return suspendCoroutine { continuation ->
            databaseReference
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

    override suspend fun getProfile(id: String): User {
        return suspendCoroutine { continuation ->
            databaseReference
                .child(id)
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
            databaseReference
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

    override suspend fun saveImage(imageProfile: String): String {
        return suspendCoroutine { continuation ->
            val uploadTask = storageReference.putFile(Uri.parse(imageProfile))
            uploadTask.addOnSuccessListener {

                storageReference.downloadUrl.addOnCompleteListener {  task ->
                    continuation.resumeWith(Result.success(task.result.toString()))
                }

            }.addOnFailureListener{
                continuation.resumeWith(Result.failure(it))
            }
        }
    }


}
