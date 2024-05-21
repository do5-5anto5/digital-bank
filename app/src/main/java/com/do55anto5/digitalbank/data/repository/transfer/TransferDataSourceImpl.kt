package com.do55anto5.digitalbank.data.repository.transfer

import com.do55anto5.digitalbank.data.model.Transfer
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class TransferDataSourceImpl @Inject constructor(
    database: FirebaseDatabase
) : TransferDataSource {

    private val transferReference = database.reference
        .child("transfer")

    override suspend fun saveTransfer(transfer: Transfer) {
        suspendCoroutine { continuation ->
            transferReference
                .child(transfer.senderUserId)
                .child(transfer.id)
                .setValue(transfer).addOnCompleteListener { senderUserTask ->
                    if (senderUserTask.isSuccessful) {
                        transferReference
                            .child(transfer.recipientUserId)
                            .child(transfer.id)
                            .setValue(transfer).addOnCompleteListener { recipientUserTask ->
                                if (recipientUserTask.isSuccessful) {
                                    continuation.resumeWith(Result.success(Unit))
                                } else {
                                    recipientUserTask.exception?.let {
                                        continuation.resumeWith(Result.failure(it))
                                    }
                                }
                            }
                    } else {
                        senderUserTask.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

    override suspend fun updateTransfer(transfer: Transfer) {
        suspendCoroutine { continuation ->
            transferReference
                .child(transfer.senderUserId)
                .child(transfer.id)
                .child("date")
                .setValue(ServerValue.TIMESTAMP)
                .addOnCompleteListener { senderTaskUpdate ->
                    if (senderTaskUpdate.isSuccessful) {

                        transferReference
                            .child(transfer.recipientUserId)
                            .child(transfer.id)
                            .child("date")
                            .setValue(ServerValue.TIMESTAMP)
                            .addOnCompleteListener { recipientTaskUpdate ->
                                if (recipientTaskUpdate.isSuccessful) {
                                    continuation.resumeWith(Result.success(Unit))
                                } else {
                                    recipientTaskUpdate.exception?.let {
                                        continuation.resumeWith(Result.failure(it))
                                    }
                                }
                            }
                    } else {
                        senderTaskUpdate.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }
}