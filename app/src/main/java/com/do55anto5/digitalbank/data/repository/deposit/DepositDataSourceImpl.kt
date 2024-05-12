package com.do55anto5.digitalbank.data.repository.deposit

import com.do55anto5.digitalbank.data.model.Deposit
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class DepositDataSourceImpl @Inject constructor(
    database: FirebaseDatabase
) : DepositDataSource {

    private val depositReference = database.reference
        .child("deposit")
        .child(FireBaseHelper.getUserId())
    override suspend fun saveDeposit(deposit: Deposit): Deposit {
        return suspendCoroutine { continuation ->
            depositReference
                .child(deposit.id)
                .setValue(deposit).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val dateReference = depositReference
                        .child(deposit.id)
                        .child("date")
                    dateReference.setValue(ServerValue.TIMESTAMP)
                        .addOnCompleteListener { taskUpdate ->
                        if (taskUpdate.isSuccessful) {

                            continuation.resumeWith(Result.success(deposit))
                        } else {
                            taskUpdate.exception?.let {
                                continuation.resumeWith(Result.failure(it))
                            }
                        }
                    }
                } else {
                    task.exception?.let {
                        continuation.resumeWith(Result.failure(it))
                    }
                }
            }
        }
    }
}