package com.do55anto5.digitalbank.data.repository.deposit

import com.do55anto5.digitalbank.data.model.Deposit
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class DepositDataSourceImpl @Inject constructor(
    database: FirebaseDatabase
) : DepositDataSource {

    private val depositReference = database.reference
        .child("deposit")
        .child(FireBaseHelper.getUserId())
    override suspend fun saveDeposit(deposit: Deposit): String {
        return suspendCoroutine { continuation ->
            depositReference
                .child(deposit.id)
                .setValue(deposit).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resumeWith(Result.success(deposit.id))
                } else {
                    task.exception?.let {
                        continuation.resumeWith(Result.failure(it))
                    }
                }
            }
        }
    }
}