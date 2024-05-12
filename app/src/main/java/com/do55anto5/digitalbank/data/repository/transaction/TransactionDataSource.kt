package com.do55anto5.digitalbank.data.repository.transaction

import com.do55anto5.digitalbank.data.model.Deposit
import com.do55anto5.digitalbank.data.model.Transaction

interface TransactionDataSource {
    suspend fun saveTransaction(transaction: Transaction)
}