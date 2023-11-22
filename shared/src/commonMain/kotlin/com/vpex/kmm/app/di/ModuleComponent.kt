package com.vpex.kmm.app.di

import com.vpex.kmm.app.data.remote.BanksRemoteDataSource
import com.vpex.kmm.app.data.repository.BanksRepository
import com.vpex.kmm.app.domain.interactor.BanksInteractor
import com.vpex.kmm.app.domain.interactor.BanksInteractorImpl
import com.vpex.kmm.app.platformModule
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        platformModule(),
        appModule()
    )
}
fun appModule() = module {

    single {
        BanksRemoteDataSource(get())
    }

    single {
        BanksRepository(get())
    }

    single<BanksInteractor> {
        BanksInteractorImpl(get())
    }

    single {
        Json { isLenient = true; ignoreUnknownKeys = true }
    }

    single {
        HttpClient(get()) {
            install(ContentNegotiation) {
                json(get())
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}


