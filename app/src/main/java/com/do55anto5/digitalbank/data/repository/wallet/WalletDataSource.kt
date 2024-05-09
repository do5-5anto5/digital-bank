package com.do55anto5.digitalbank.data.repository.wallet

import com.do55anto5.digitalbank.data.model.Wallet

interface WalletDataSource {

    suspend fun initWallet(wallet: Wallet)

    suspend fun getWallet() : Wallet

}