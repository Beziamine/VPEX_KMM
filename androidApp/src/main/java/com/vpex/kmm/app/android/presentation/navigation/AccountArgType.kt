package com.vpex.kmm.app.android.presentation.navigation

import com.google.gson.Gson
import com.vpex.kmm.app.data.model.banks.Accounts

class AccountArgType : JsonNavType<Accounts>() {
    override fun fromJsonParse(value: String): Accounts = Gson().fromJson(value, Accounts::class.java)

    override fun Accounts.getJsonParse(): String = Gson().toJson(this)
}