package com.vpex.kmm.app.android.presentation.main

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.vpex.kmm.app.android.R
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
        Text(modifier = Modifier.padding(20.dp),
            text = stringResource(R.string.mes_comptes),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row (modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(10.dp)){
            Text(
                text = stringResource(R.string.credit_agricole),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.caption
            )
        }
        LazyColumn(
            modifier = Modifier.padding(horizontal = 0.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
        ) {
            items(
                items = banks.filter { it.isCA == 1 }.sortedBy { it.name }
            ) { bank ->
                BankItem(bank, navController)
            }
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(10.dp)){
            Text(
                text = stringResource(R.string.autres_banques),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.caption
            )
        }
        LazyColumn(
            modifier = Modifier.padding(horizontal = 0.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
        ) {
            items(
                items = banks.filter { it.isCA == 0 }.sortedBy { it.name }
            ) { bank ->
                BankItem(bank, navController)
            }
        }
    }
}

@Composable
fun BankItem(bank : Banks, navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = 1.dp
    ) {
        Column {
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)){
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = bank.name!!,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.caption
                )
                IconButton(modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { expanded = !expanded }) {
                    Icon(painter = if (expanded) painterResource(id = R.drawable.expand_less) else painterResource(id = R.drawable.expand_more),
                        contentDescription = if (expanded) {
                            stringResource(R.string.show_less)
                        } else {
                            stringResource(R.string.show_more)
                        })
                }
            }
            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.onPrimary)
            )
            if(expanded){
                Column {
                    bank.accounts.sortedBy { it.label }.forEach {
                        AccountItem(it, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountItem(account : Accounts, navController: NavHostController) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = 1.dp
    ) {
        Column (Modifier.padding(start = 30.dp)){
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)){
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = account.label!!,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.caption
                )
                Row (modifier = Modifier.align(Alignment.CenterEnd)){
                    Text(
                        modifier = Modifier.align(CenterVertically),
                        text = account.balance!!.toString() + stringResource(R.string.currency),
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.caption
                    )
                    IconButton(
                        modifier = Modifier.align(CenterVertically),
                        onClick = {
                            val json = Uri.encode(Gson().toJson(account))
                            navController.navigate(route = Screen.Detail.passAccount(json))
                        }) {
                        Icon(painter = painterResource(id = R.drawable.navigate_next),
                            contentDescription = stringResource(R.string.navigate_next)
                        )
                    }
                }
            }
            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.onPrimary)
            )
        }
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
                ),
                Accounts(
                    balance = 2031.84,
                    contractNumber = "32216549871",
                    holder = "Corinne Martin",
                    id = "151515151151",
                    label = "Compte de dépôt 2",
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
                    label = "Compte de dépôt 3",
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
                ),Accounts(
                    balance = 2031.84,
                    contractNumber = "32216549871",
                    holder = "Corinne Martin",
                    id = "09878900000",
                    label = "Compte de dépôt 4",
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