package com.vpex.kmm.app.android.presentation.main

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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vpex.kmm.app.android.R
import com.vpex.kmm.app.android.presentation.utils.Utils.getDateTime
import com.vpex.kmm.app.data.model.banks.Accounts
import com.vpex.kmm.app.data.model.banks.Operations

@Composable
fun DetailScreen(
    navController: NavHostController,
    account: Accounts?
){
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
            DetailContent(navController,account)
        }
    }
}

@Composable
fun DetailContent(
    navController: NavHostController,
    account: Accounts?
) {
    Column(Modifier.fillMaxSize()){
        Row (Modifier.padding(top = 20.dp)){
            IconButton(
                onClick = {
                    navController.navigateUp()
                }) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .align(CenterVertically),
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colors.surface
                )
            }
            Text(
                modifier = Modifier.align(CenterVertically),
                text = stringResource(R.string.mes_comptes),
                color = MaterialTheme.colors.surface,
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.align(CenterHorizontally),
            text = account?.balance.toString() + stringResource(R.string.currency),
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.align(CenterHorizontally),
            text = account?.label!!,
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(40.dp))
        LazyColumn(
            modifier = Modifier.padding(horizontal = 0.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
        ) {
            items(
                items = account.operations
            ) { operation ->
                OperationItem(operation)
            }
        }
    }
}

@Composable
fun OperationItem(operation : Operations) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.onSurface,
        elevation = 1.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(Modifier.align(Alignment.CenterStart)) {
                    Text(
                        text = operation.title!!,
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = getDateTime(operation.date!!),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.caption
                    )
                }
                Text(
                    modifier = Modifier.align(CenterEnd),
                    text = operation.amount!! + stringResource(R.string.currency),
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.caption
                )
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