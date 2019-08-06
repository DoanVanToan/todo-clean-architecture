package com.toandoan.data.local

import com.toandoan.data.model.TaskEnity
import com.toandoan.data.model.TaskEnityMapper
import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import io.reactivex.Completable
import io.reactivex.Single
import kotlin.random.Random

class TaskLocalDataSource constructor(
    private val taskDAO: TaskDAO,
    private val mapper: TaskEnityMapper
) : TaskRepository {
    override fun deleteTasks(): Completable {
        return Completable.fromAction {
            taskDAO.deleteTasks()
        }
    }

    override fun getTasks(query: String): Single<List<Task>> {
        return Single.fromCallable {
            taskDAO.getTasks().map { mapper.mapToDomain(it) }
        }
    }

    override fun insertTask(title: String, isDone: Boolean): Single<Task> {
        return Single.fromCallable {
            val task = TaskEnity(id = Random.nextLong(), title = title, isDone = isDone)
            taskDAO.insertTask(task)
            mapper.mapToDomain(task)
        }
    }

    override fun isExistTask(title: String): Single<Boolean> {
        return Single.fromCallable {
            taskDAO.countTask(title) > 0
        }
    }

    override fun getTasks(): Single<List<Task>> {
        return Single.fromCallable{
            taskDAO.getTasks().map { mapper.mapToDomain(it) }
        }
    }

}