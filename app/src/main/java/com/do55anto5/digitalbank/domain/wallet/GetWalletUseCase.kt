package com.do55anto5.digitalbank.domain.wallet

import com.do55anto5.digitalbank.data.model.Wallet
import com.do55anto5.digitalbank.data.repository.wallet.WalletDataSourceImpl
import javax.inject.Inject

class GetWalletUseCase @Inject constructor(
    private val walletDataSourceImpl: WalletDataSourceImpl
) {

    suspend operator fun invoke() : Wallet {
        return walletDataSourceImpl.getWallet()
    }

}