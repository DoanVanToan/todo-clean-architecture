package com.toandoan.data.repository.task

import com.toandoan.data.local.TaskLocalDataSource
import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import io.reactivex.Completable

class TaskRepositoryImpl constructor(
    private val taskLocalDataSource: TaskLocalDataSource
) : TaskRepository {
    override fun deleteTask(task: Task) = taskLocalDataSource.deleteTask(task)

    override fun deleteTasks(): Completable = taskLocalDataSource.deleteTasks()

    override fun isExistTask(title: String) = taskLocalDataSource.isExistTask(title)

    override fun getTasks(query: String) = taskLocalDataSource.getTasks(query)

    override fun insertTask(title: String, isDone: Boolean) =
        taskLocalDataSource.insertTask(title, isDone)

    override fun getTasks() = taskLocalDataSource.getTasks()
}