package com.do55anto5.digitalbank.domain.transfer

import com.do55anto5.digitalbank.data.model.Transfer
import com.do55anto5.digitalbank.data.repository.transfer.TransferDataSourceImpl
import javax.inject.Inject

class GetTransferUseCase @Inject constructor(
    private val transferDataSourceImpl: TransferDataSourceImpl
) {

    suspend operator fun invoke(id: String): Transfer {
        return transferDataSourceImpl.getTransfer(id)
    }
}