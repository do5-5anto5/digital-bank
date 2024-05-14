package com.do55anto5.digitalbank.data.model

import com.do55anto5.digitalbank.data.enum.TransactionOperation
import com.do55anto5.digitalbank.data.enum.TransactionType

data class Transaction(
    var id: String = "",
    val operation: TransactionOperation? = null,
    val date: Long = 0L,
    val amount: Float = 0f,
    var type: TransactionType? = null
)