package com.toandoan.cleanarchitechture.di

import com.toandoan.domain.usecase.task.CreateTaskUseCase
import com.toandoan.domain.usecase.task.DeleteAllTaskUseCase
import com.toandoan.domain.usecase.task.GetTaskUseCase
import com.toandoan.domain.usecase.task.SearchTaskUseCase
import org.koin.dsl.module

val appModule = module {
    single { CreateTaskUseCase(get()) }

    single { GetTaskUseCase(get()) }

    single { SearchTaskUseCase(get()) }

    single { DeleteAllTaskUseCase(get()) }
}