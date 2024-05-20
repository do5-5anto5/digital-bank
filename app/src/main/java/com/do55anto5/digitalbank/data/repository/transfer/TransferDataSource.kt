package com.do55anto5.digitalbank.data.repository.transfer

import com.do55anto5.digitalbank.data.model.Transfer

interface TransferDataSource {

    suspend fun saveTransfer(transfer: Transfer)
}