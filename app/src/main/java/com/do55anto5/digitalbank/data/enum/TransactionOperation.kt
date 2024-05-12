package com.do55anto5.digitalbank.data.enum

import com.do55anto5.digitalbank.R

enum class TransactionOperation {
    DEPOSIT;

    companion object {
        fun getOperation(operation: TransactionOperation): String {
            return when (operation) {
                DEPOSIT -> R.string.deposit_form_fragment_deposit_operation.toString()
            }
        }
    }
}