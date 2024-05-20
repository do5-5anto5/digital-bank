package com.do55anto5.digitalbank.data.repository.transfer

import com.do55anto5.digitalbank.data.model.Transfer
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class TransferDataSourceImpl @Inject constructor(
    database: FirebaseDatabase
): TransferDataSource {

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
}