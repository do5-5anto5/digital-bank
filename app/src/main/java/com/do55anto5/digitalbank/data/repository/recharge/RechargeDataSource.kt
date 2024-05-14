package com.do55anto5.digitalbank.data.repository.recharge

import com.do55anto5.digitalbank.data.model.Recharge

interface RechargeDataSource {

    suspend fun saveRecharge(recharge: Recharge) : Recharge

    suspend fun getRecharge(id: String) : Recharge
}