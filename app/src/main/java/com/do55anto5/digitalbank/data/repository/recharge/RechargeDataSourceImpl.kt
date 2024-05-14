package com.do55anto5.digitalbank.data.repository.recharge

import com.do55anto5.digitalbank.data.model.Recharge
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class RechargeDataSourceImpl @Inject constructor(
    database: FirebaseDatabase
) : RechargeDataSource {

    private val rechargeReference = database.reference
        .child("recharge")
        .child(FireBaseHelper.getUserId())

    override suspend fun saveRecharge(recharge: Recharge): Recharge {
        return suspendCoroutine { continuation ->
            rechargeReference
                .child(recharge.id)
                .setValue(recharge).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val dateReference = rechargeReference
                            .child(recharge.id)
                            .child("date")
                        dateReference.setValue(ServerValue.TIMESTAMP)
                            .addOnCompleteListener { taskUpdate ->
                                if (taskUpdate.isSuccessful) {

                                    continuation.resumeWith(Result.success(recharge))
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

    override suspend fun getRecharge(id: String): Recharge {
        TODO("Not yet implemented")
    }
}