package com.vpex.kmm.app.android.presentation.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Detail : Screen("detail/{account}"){
        fun passAccount(account: String) = "detail/$account"
    }
}