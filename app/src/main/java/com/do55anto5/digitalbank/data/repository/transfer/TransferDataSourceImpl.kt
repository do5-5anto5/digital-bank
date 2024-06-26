package com.do55anto5.digitalbank.data.repository.transfer

import com.do55anto5.digitalbank.data.enum.TransactionOperation
import com.do55anto5.digitalbank.data.enum.TransactionType
import com.do55anto5.digitalbank.data.model.Transaction
import com.do55anto5.digitalbank.data.model.Transfer
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class TransferDataSourceImpl @Inject constructor(
    database: FirebaseDatabase
) : TransferDataSource {

    private val transferReference = database.reference
        .child("transfer")

    private val transactionReference = database.reference
        .child("transaction")

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

    override suspend fun getTransfer(id: String): Transfer {
        return suspendCoroutine { continuation ->
            transferReference
                .child(FireBaseHelper.getUserId())
                .child(id)
                .addListenerForSingleValueEvent (object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val transfer = snapshot.getValue(Transfer::class.java)

                        transfer?.let {
                            continuation.resumeWith(Result.success(it))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWith(Result.failure(error.toException()))
                    }

                })
        }
    }

    override suspend fun saveTransferTransaction(transfer: Transfer) {
        return suspendCoroutine { continuation ->
            val senderUserTransaction = Transaction(
                id = transfer.id,
                operation = TransactionOperation.TRANSFER,
                date = transfer.date,
                amount = transfer.amount,
                type = TransactionType.CASH_OUT
            )

            val recipientUserTransaction = Transaction(
                id = transfer.id,
                operation = TransactionOperation.TRANSFER,
                date = transfer.date,
                amount = transfer.amount,
                type = TransactionType.CASH_IN
            )

            transactionReference
                .child(transfer.senderUserId)
                .child(transfer.id)
                .setValue(senderUserTransaction)
                .addOnCompleteListener { senderUserTask ->
                    if (senderUserTask.isSuccessful) {

                        transactionReference
                            .child(transfer.recipientUserId)
                            .child(transfer.id)
                            .setValue(recipientUserTransaction)
                            .addOnCompleteListener { recipientUserTask ->
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

    override suspend fun updateTransferTransaction(transfer: Transfer) {
        suspendCoroutine { continuation ->
            transactionReference
                .child(transfer.senderUserId)
                .child(transfer.id)
                .child("date")
                .setValue(ServerValue.TIMESTAMP)
                .addOnCompleteListener { senderTaskUpdate ->
                    if (senderTaskUpdate.isSuccessful) {

                        transactionReference
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