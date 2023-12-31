package com.vpex.kmm.app.data.repository

import com.vpex.kmm.app.data.model.banks.Banks
import com.vpex.kmm.app.data.remote.BanksRemoteDataSource

class BanksRepository(
    private val banksRemoteDataSource: BanksRemoteDataSource,
) {
    suspend fun getAllBanks(): List<Banks> {
        return banksRemoteDataSource.getAllBanks()!!
    }
}