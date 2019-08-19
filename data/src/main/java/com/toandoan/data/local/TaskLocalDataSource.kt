package com.toandoan.data.local

import com.toandoan.data.model.task.TaskEnity
import com.toandoan.data.model.task.TaskEnityMapper
import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import kotlin.random.Random

class TaskLocalDataSource constructor(
    private val taskDAO: TaskDAO,
    private val mapper: TaskEnityMapper
) : TaskRepository {

    override fun isExistTask(title: String) = taskDAO.countTask(title) > 0

    override fun getTasks(query: String): List<Task> {
        return taskDAO.getTasks(query).map {
            mapper.mapToDomain(it)
        }
    }

    override fun insertTask(title: String, isDone: Boolean): Task {
        val task = TaskEnity(
            id = Random.nextLong(),
            title = title,
            isDone = isDone
        )
        taskDAO.insertTask(task)
        return mapper.mapToDomain(task)
    }

    override fun getTasks(): List<Task> {
        return taskDAO.getTasks().map {
            mapper.mapToDomain(it)
        }
    }

}