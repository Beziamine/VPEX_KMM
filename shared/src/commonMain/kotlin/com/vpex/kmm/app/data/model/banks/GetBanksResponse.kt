package com.vpex.kmm.app.data.model.banks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetBanksResponse(
    @SerialName("banks") var banks: List<Banks>
)