package com.vpex.kmm.app.data.model.banks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Banks(
    @SerialName("name") var name : String? = null,
    @SerialName("isCA") var isCA : Int? = null,
    @SerialName("accounts") var accounts : List<Accounts>
)