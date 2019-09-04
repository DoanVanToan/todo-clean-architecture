package com.toandoan.cleanarchitechture.di

import com.toandoan.cleanarchitechture.base.AppScheduler
import com.toandoan.cleanarchitechture.base.AppSchedulerImpl
import com.toandoan.cleanarchitechture.model.TaskItemMapper
import com.toandoan.data.local.TaskDatabase
import com.toandoan.data.local.TaskLocalDataSource
import com.toandoan.data.model.TaskEnityMapper
import com.toandoan.data.repository.task.TaskRepositoryImpl
import com.toandoan.domain.repository.TaskRepository
import org.koin.dsl.module

val dataModule = module {
    // Provide task database
    single { TaskDatabase.getInstance(get()) }

    single { (get() as TaskDatabase).taskDAO() }

    // provide task enity mapper to map
    factory { TaskEnityMapper() }

    // provide task item mapper
    factory { TaskItemMapper() }

    // provide Scheduler for Rx
    factory<AppScheduler> { AppSchedulerImpl() }

    // provide task local datasource for task repository
    factory { TaskLocalDataSource(get(), get()) }

    // provide task repository for use case
    factory<TaskRepository> { TaskRepositoryImpl(get()) }

}