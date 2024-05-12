package com.do55anto5.digitalbank.data.enum

enum class TransactionOperation {
    DEPOSIT;

    companion object {
        fun getOperation(operation: TransactionOperation): String {
            return when (operation) {
                DEPOSIT -> operation.name
            }
        }
    }
}