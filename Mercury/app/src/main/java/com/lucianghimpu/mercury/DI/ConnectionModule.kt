package com.lucianghimpu.mercury.DI

import com.lucianghimpu.mercury.UI.Connection.ConnectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val connectionModule = module {
    viewModel { ConnectionViewModel(get()) }
}