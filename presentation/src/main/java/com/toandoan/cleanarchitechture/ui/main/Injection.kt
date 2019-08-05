package com.toandoan.cleanarchitechture.ui.main

import androidx.appcompat.app.AppCompatActivity
import com.toandoan.cleanarchitechture.enity.TaskItemMapper
import com.toandoan.data.local.TaskDatabase
import com.toandoan.data.local.TaskLocalDataSource
import com.toandoan.data.model.TaskEnityMapper
import com.toandoan.data.repository.task.TaskRepositoryImpl
import com.toandoan.domain.usecase.task.CreateTaskUseCase
import com.toandoan.domain.usecase.task.GetTaskUseCase
import com.toandoan.domain.usecase.task.SearchTaskUseCase

object Injection {
    lateinit var activity: AppCompatActivity

    val database by lazy { TaskDatabase.getInstance(activity.applicationContext) }

    val mapper by lazy { TaskEnityMapper() }

    val itemMapper by lazy { TaskItemMapper() }

    val localDataSource by lazy {
        TaskLocalDataSource(
            database.taskDAO(),
            mapper
        )
    }

    val repository by lazy { TaskRepositoryImpl(localDataSource) }

    val createTaskUseCase by lazy { CreateTaskUseCase(repository) }

    val getTaskUseCase by lazy { GetTaskUseCase(repository) }

    val searchTaskUseCase by lazy { SearchTaskUseCase(repository) }

    fun inject(mainActivity: MainActivity) {
        activity = mainActivity
        mainActivity.viewModel = TaskViewModelFactory(
            getTaskUseCase,
            createTaskUseCase,
            itemMapper
        ).create(TaskViewModel::class.java)
    }
}