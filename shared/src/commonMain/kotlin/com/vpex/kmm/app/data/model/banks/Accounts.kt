package com.vpex.kmm.app.data.model.banks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Accounts(
    @SerialName("order") var order : Int? = null,
    @SerialName("id") var id : String? = null,
    @SerialName("holder") var holder : String? = null,
    @SerialName("role") var role : Int? = null,
    @SerialName("contract_number") var contractNumber : String? = null,
    @SerialName("label") var label : String? = null,
    @SerialName("product_code") var productCode : String? = null,
    @SerialName("balance") var balance : Double? = null,
    @SerialName("operations") var operations : List<Operations>
)