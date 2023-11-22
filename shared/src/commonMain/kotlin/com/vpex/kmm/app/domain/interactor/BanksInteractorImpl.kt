package com.vpex.kmm.app.domain.interactor

import com.vpex.kmm.app.data.model.banks.Banks
import com.vpex.kmm.app.data.repository.BanksRepository

class BanksInteractorImpl(private val banksRepository: BanksRepository) : BanksInteractor {

    override suspend fun getAllBanks(): List<Banks> {
        return banksRepository.getAllBanks()
    }
}