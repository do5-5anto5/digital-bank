package com.do55anto5.digitalbank.domain.transaction

import com.do55anto5.digitalbank.data.model.Transaction
import com.do55anto5.digitalbank.data.repository.transaction.TransactionDataSourceImpl
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionDataSourceImpl: TransactionDataSourceImpl
) {

    suspend operator fun invoke() : List<Transaction> {
        return transactionDataSourceImpl.getTransactions()
    }

}