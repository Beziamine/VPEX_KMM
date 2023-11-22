package com.vpex.kmm.app.android.presentation.main

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.vpex.kmm.app.android.presentation.navigation.Screen
import com.vpex.kmm.app.data.model.banks.Accounts
import com.vpex.kmm.app.data.model.banks.Banks
import com.vpex.kmm.app.data.model.banks.Operations
import com.vpex.kmm.app.viewmodel.BanksViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: BanksViewModel = getViewModel()
){
    //val allBanksAsync by viewModel.allBanks.collectAsState
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        scaffoldState = scaffoldState, snackbarHost = {
            scaffoldState.snackbarHostState
        }
    ) { innerPadding ->
        Box (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colors.onPrimary)
        ){
            MainContent(
                navController = navController,
                banks = staticValue()
            )
        }
    }

//        when (val async = allBanksAsync) {
//            is AsyncResult.Error -> {
//                println("error")
//            }
//
//            is AsyncResult.Success<*> -> {
//                println("success")
//                MainContent(
//                    banks = async.data as List<Banks>
//                )
//            }
//
//            is AsyncResult.Loading -> {
//                //LoadingScreen(modifier = modifier)
//            }
//
//            else -> {
//                println("test")
//            }
//        }
//    }
}

@Composable
fun MainContent(navController: NavHostController,
                banks: List<Banks>
){
    Column(Modifier.fillMaxSize()){
        Text(modifier = Modifier
            .padding(20.dp)
            .clickable {
                val account = banks[0].accounts[0]
                val json = Uri.encode(Gson().toJson(account))
                navController.navigate(route = Screen.Detail.passAccount(json))
            },
            text = "Mes Comptes",
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1
        )
    }
}

fun staticValue(): List<Banks> {
    return listOf(
        Banks(
            name = "CA Languedoc",
            isCA = 1,
            accounts = listOf(
                Accounts(
                    balance = 2031.84,
                    contractNumber = "32216549871",
                    holder = "Corinne Martin",
                    id = "151515151151",
                    label = "Compte de dépôt",
                    operations = listOf(
                        Operations(
                            amount = "-15,99",
                            category = "leisure",
                            date = "1644870724",
                            id = "2",
                            title = "Prélèvement Netflix"
                        ),
                        Operations(
                            amount = "-95,99",
                            category = "online",
                            date = "1644611558",
                            id = "4",
                            title = "CB Amazon"
                        )
                    ),
                    order = 1,
                    productCode = "00004",
                    role = 1
                )
            )
        ),
        Banks(
            name = "Boursorama",
            isCA = 0,
            accounts = listOf(
                Accounts(
                    balance = 2031.84,
                    contractNumber = "32216549871",
                    holder = "Corinne Martin",
                    id = "09878900000",
                    label = "Compte de dépôt",
                    operations = listOf(
                        Operations(
                            amount = "-15,99",
                            category = "leisure",
                            date = "1644870724",
                            id = "2",
                            title = "Prélèvement Netflix"
                        ),
                        Operations(
                            amount = "-95,99",
                            category = "online",
                            date = "1644611558",
                            id = "4",
                            title = "CB Amazon"
                        )
                    ),
                    order = 1,
                    productCode = "00004",
                    role = 1
                )
            )
        ),
        Banks(
            name = "Banque Pop",
            isCA = 0,
            accounts = listOf(
                Accounts(
                    balance = 2031.84,
                    contractNumber = "32216549871",
                    holder = "Corinne Martin",
                    id = "3982997777",
                    label = "Compte de dépôt",
                    operations = listOf(
                        Operations(
                            amount = "-15,99",
                            category = "leisure",
                            date = "1644870724",
                            id = "2",
                            title = "Prélèvement Netflix"
                        ),
                        Operations(
                            amount = "-95,99",
                            category = "online",
                            date = "1644611558",
                            id = "4",
                            title = "CB Amazon"
                        )
                    ),
                    order = 1,
                    productCode = "00004",
                    role = 1
                )
            )
        ),
        Banks(
            name = "CA Centre-Est",
            isCA = 1,
            accounts = listOf(
                Accounts(
                    balance = 2031.84,
                    contractNumber = "32216549871",
                    holder = "Corinne Martin",
                    id = "3982938",
                    label = "Compte de dépôt",
                    operations = listOf(
                        Operations(
                            amount = "-15,99",
                            category = "leisure",
                            date = "1644870724",
                            id = "2",
                            title = "Prélèvement Netflix"
                        ),
                        Operations(
                            amount = "-95,99",
                            category = "online",
                            date = "1644611558",
                            id = "4",
                            title = "CB Amazon"
                        )
                    ),
                    order = 1,
                    productCode = "00004",
                    role = 1
                )
            )
        )
    )
}