package com.vpex.kmm.app.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.vpex.kmm.app.domain.async.AsyncResult
import com.vpex.kmm.app.domain.interactor.BanksInteractor
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BanksViewModel(
    private val banksInteractor: BanksInteractor,
) : ViewModel() {

    private val allBanksMutable = MutableStateFlow<AsyncResult>(AsyncResult.Uninitialized)
    val allBanks = allBanksMutable.asStateFlow()

    init {
        getAllBanks()
    }

    @NativeCoroutines
    private fun getAllBanks() {
//        allBanksMutable.value = AsyncResult.Loading
//        viewModelScope.launch {
//            try {
//                val banks = banksInteractor.getAllBanks()
//                allBanksMutable.value = AsyncResult.Success(banks)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                allBanksMutable.value = AsyncResult.Error(e.message.orEmpty())
//            }
//        }
    }

}