package com.do55anto5.digitalbank.data.enum

enum class TransactionOperation {
    DEPOSIT,
    RECHARGE,
    TRANSFER;

    companion object {
        fun getOperation(operation: TransactionOperation): String {

            return when (operation) {
                DEPOSIT -> "DEPÓSITO"
                RECHARGE -> "RECARGA DE CELULAR"
                TRANSFER -> "TRANSFERÊNCIA"
            }
        }
    }
}