package com.do55anto5.digitalbank.data.enum

enum class TransactionOperation {
    DEPOSIT,
    RECHARGE;

    companion object {
        fun getOperation(operation: TransactionOperation): String {
            return when (operation) {
                DEPOSIT -> operation.name
                RECHARGE -> operation.name
            }
        }
    }
}