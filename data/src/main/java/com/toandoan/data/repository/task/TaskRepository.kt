package com.toandoan.data.repository.task

import com.toandoan.data.local.TaskLocalDataSource
import com.toandoan.domain.repository.TaskRepository

class TaskRepositoryImpl constructor(
    private val taskLocalDataSource: TaskLocalDataSource
) : TaskRepository {
    override fun isExistTask(title: String): Boolean {
        return taskLocalDataSource.isExistTask(title)
    }

    override fun getTasks(query: String) = taskLocalDataSource.getTasks(query)

    override fun insertTask(title: String, isDone: Boolean) = taskLocalDataSource.insertTask(title, isDone)

    override fun getTasks() = taskLocalDataSource.getTasks()
}