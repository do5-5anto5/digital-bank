package com.do55anto5.digitalbank.domain.deposit

import com.do55anto5.digitalbank.data.model.Deposit
import com.do55anto5.digitalbank.data.repository.deposit.DepositDataSourceImpl
import javax.inject.Inject

class SaveDepositUseCase @Inject constructor(
    private val depositDataSourceImpl: DepositDataSourceImpl
) {

    suspend operator fun invoke(deposit: Deposit) : Deposit {
        return depositDataSourceImpl.saveDeposit(deposit)
    }

}