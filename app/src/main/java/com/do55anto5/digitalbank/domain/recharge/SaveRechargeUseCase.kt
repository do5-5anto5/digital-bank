package com.do55anto5.digitalbank.domain.recharge

import com.do55anto5.digitalbank.data.model.Recharge
import com.do55anto5.digitalbank.data.repository.recharge.RechargeDataSourceImpl
import javax.inject.Inject

class SaveRechargeUseCase @Inject constructor(
    private val rechargeDataSourceImpl: RechargeDataSourceImpl
) {

    suspend operator fun invoke (recharge: Recharge) : Recharge {
        return rechargeDataSourceImpl.saveRecharge(recharge)
    }
}