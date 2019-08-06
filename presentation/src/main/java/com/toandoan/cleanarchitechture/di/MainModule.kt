package com.toandoan.cleanarchitechture.di

import com.toandoan.cleanarchitechture.ui.main.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        TaskViewModel(get(), get(), get(), get(), get())
    }
}