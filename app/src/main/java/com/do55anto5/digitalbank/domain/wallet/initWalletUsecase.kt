package com.do55anto5.digitalbank.domain.wallet

import com.do55anto5.digitalbank.data.model.Wallet
import com.do55anto5.digitalbank.data.repository.wallet.WalletDataSourceImpl
import javax.inject.Inject

class InitWalletUsecase @Inject constructor(
    private val walletDataSourceImpl: WalletDataSourceImpl
) {

    suspend operator fun invoke(wallet: Wallet) {
        return walletDataSourceImpl.initWallet(wallet)
    }
}