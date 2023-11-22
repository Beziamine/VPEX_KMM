package com.vpex.kmm.app.domain.interactor

import com.vpex.kmm.app.data.model.banks.Banks

interface BanksInteractor {
    suspend fun getAllBanks(
    ): List<Banks>
}