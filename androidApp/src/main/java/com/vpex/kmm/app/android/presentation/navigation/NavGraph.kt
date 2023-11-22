package com.vpex.kmm.app.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.vpex.kmm.app.android.presentation.main.DetailScreen
import com.vpex.kmm.app.android.presentation.main.MainScreen
import com.vpex.kmm.app.data.model.banks.Accounts


@Composable
fun MainNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("account") {
                type = AccountArgType()
            })
        ) { navBackStackEntry ->
            val account = navBackStackEntry.arguments?.getString("account")
                ?.let { Gson().fromJson(it, Accounts::class.java) }
            DetailScreen(navController, account)
        }
    }
}