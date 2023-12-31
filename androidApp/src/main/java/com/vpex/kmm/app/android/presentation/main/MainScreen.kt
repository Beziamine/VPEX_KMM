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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.vpex.kmm.app.android.R
import com.vpex.kmm.app.android.presentation.navigation.Screen
import com.vpex.kmm.app.data.model.banks.Accounts
import com.vpex.kmm.app.data.model.banks.Banks
import com.vpex.kmm.app.domain.async.AsyncResult
import com.vpex.kmm.app.viewmodel.BanksViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: BanksViewModel = getViewModel()
){
    val allBanksAsync by viewModel.allBanks.collectAsState()
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
            .background(MaterialTheme.colors.secondaryVariant)
        ){
            when (val async = allBanksAsync) {
                is AsyncResult.Error -> {
                    ErrorContent()
                }
                is AsyncResult.Success<*> -> {
                    MainContent(navController = navController, banks = async.data as List<Banks>)
                }
                is AsyncResult.Loading -> {
                    LoadingContent()
                }
                else -> {}
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Box(Modifier.fillMaxSize()){
        Column (Modifier.align(Center)){
            CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally),
                color = MaterialTheme.colors.primaryVariant)
            Spacer(modifier = Modifier.height(20.dp))
            Text(modifier = Modifier
                .align(CenterHorizontally)
                .padding(horizontal = 20.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.chargement),
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun ErrorContent() {
    Box (Modifier.fillMaxSize()){
        Text(modifier = Modifier
            .align(Center)
            .padding(horizontal = 20.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.erreur_text),
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun MainContent(navController: NavHostController,
                banks: List<Banks>
){
    Column(Modifier.fillMaxSize()){
        Text(modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
            text = stringResource(R.string.mes_comptes),
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row (modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(10.dp)){
            Text(
                text = stringResource(R.string.credit_agricole),
                color = MaterialTheme.colors.secondary,
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
                color = MaterialTheme.colors.secondary,
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
        backgroundColor = MaterialTheme.colors.onSurface,
        elevation = 1.dp
    ) {
        Column {
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)){
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = bank.name!!,
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.caption
                )
                IconButton(modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { expanded = !expanded }) {
                    Icon(
                        tint = MaterialTheme.colors.primaryVariant,
                        painter = if (expanded) painterResource(id = R.drawable.expand_less) else painterResource(id = R.drawable.expand_more),
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
                    .background(MaterialTheme.colors.error)
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
        backgroundColor = MaterialTheme.colors.onSurface,
        elevation = 1.dp
    ) {
        Column (Modifier.padding(start = 30.dp)){
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)){
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = account.label!!,
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.caption
                )
                Row (modifier = Modifier.align(Alignment.CenterEnd)){
                    Text(
                        modifier = Modifier.align(CenterVertically),
                        text = account.balance!!.toString() + stringResource(R.string.currency),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.caption
                    )
                    IconButton(
                        modifier = Modifier.align(CenterVertically),
                        onClick = {
                            val json = Uri.encode(Gson().toJson(account))
                            navController.navigate(route = Screen.Detail.passAccount(json))
                        }) {
                        Icon(tint = MaterialTheme.colors.primaryVariant,
                            painter = painterResource(id = R.drawable.navigate_next),
                            contentDescription = stringResource(R.string.navigate_next)
                        )
                    }
                }
            }
            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.error)
            )
        }
    }
}