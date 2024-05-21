package com.do55anto5.digitalbank.data.repository.transfer

import com.do55anto5.digitalbank.data.model.Transfer

interface TransferDataSource {

    suspend fun saveTransfer(transfer: Transfer)
    suspend fun updateTransfer(transfer: Transfer)
    suspend fun getTransfer(id: String): Transfer
    suspend fun saveTransferTransaction(transfer: Transfer)
}