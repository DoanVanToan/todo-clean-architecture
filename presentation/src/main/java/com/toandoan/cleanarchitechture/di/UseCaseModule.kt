package com.toandoan.cleanarchitechture.di

import com.toandoan.domain.usecase.task.*
import org.koin.dsl.module

val appModule = module {
    single<CreateTaskUseCase> { CreateTaskUseCaseImpl(get()) }

    single<GetTaskUseCase> { GetTaskUseCaseImpl(get()) }

    single<SearchTaskUseCase> { SearchTaskUseCaseImpl(get()) }

    single<DeleteAllTaskUseCase> { DeleteAllTaskUseCaseImpl(get()) }

    single<DeleteTaskUseCase> { DeleteTaskUseCaseImpl(get()) }
}