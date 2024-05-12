package com.do55anto5.digitalbank.data.enum

import com.do55anto5.digitalbank.R

enum class TransactionType {
    CASH_IN, CASH_OUT;

    companion object {
        fun getType(operation: TransactionOperation): String {
            return when (operation) {
                TransactionOperation.DEPOSIT ->
                    R.string.deposit_form_fragment_deposit_operation_char.toString()
            }
        }
    }
}