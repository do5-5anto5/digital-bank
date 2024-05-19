package com.do55anto5.digitalbank.domain.transaction

import com.do55anto5.digitalbank.data.enum.TransactionType
import com.do55anto5.digitalbank.data.repository.transaction.TransactionDataSourceImpl
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val transactionDataSourceImpl: TransactionDataSourceImpl
) {

    suspend operator fun invoke(): Float {
        var cashIn = 0f
        var cashOut = 0f

        transactionDataSourceImpl.getTransactions().forEach { transaction ->
            if (transaction.type == TransactionType.CASH_IN) {
                cashIn += transaction.amount
            } else {
                cashOut += transaction.amount
            }
        }
        return cashIn - cashOut
    }
}