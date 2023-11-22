package com.vpex.kmm.app.data.remote

import com.vpex.kmm.app.data.constant.NetworkConstant
import com.vpex.kmm.app.data.model.banks.Banks
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class BanksRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getAllBanks(
    ): List<Banks>? {
        return httpClient.get {
            url(NetworkConstant.GET_ALL_BANKS_URL)
        }.body()
    }
}