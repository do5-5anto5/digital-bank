package com.do55anto5.digitalbank.data.repository.deposit

import com.do55anto5.digitalbank.data.model.Deposit

interface DepositDataSource {

     suspend fun saveDeposit(deposit: Deposit) : Deposit
}