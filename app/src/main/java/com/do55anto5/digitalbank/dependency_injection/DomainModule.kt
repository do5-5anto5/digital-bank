package com.do55anto5.digitalbank.dependency_injection

import com.do55anto5.digitalbank.data.repository.auth.AuthFirebaseDataSource
import com.do55anto5.digitalbank.data.repository.auth.AuthFirebaseDataSourceImpl
import com.do55anto5.digitalbank.data.repository.deposit.DepositDataSource
import com.do55anto5.digitalbank.data.repository.deposit.DepositDataSourceImpl
import com.do55anto5.digitalbank.data.repository.recharge.RechargeDataSource
import com.do55anto5.digitalbank.data.repository.recharge.RechargeDataSourceImpl
import com.do55anto5.digitalbank.data.repository.transaction.TransactionDataSource
import com.do55anto5.digitalbank.data.repository.transaction.TransactionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindAuthDataSource(
        authFirebaseDataSourceImpl: AuthFirebaseDataSourceImpl
    ) : AuthFirebaseDataSource

    @Binds
    abstract fun bindDepositDataSource(
        depositDataSourceImpl: DepositDataSourceImpl
    ) : DepositDataSource

    @Binds
    abstract fun bindTransactionDataSource(
        transactionDataSourceImpl: TransactionDataSourceImpl
    ) : TransactionDataSource

    @Binds
    abstract fun bindRechargeDataSource(
        rechargeDataSourceImpl: RechargeDataSourceImpl
    ) : RechargeDataSource
}