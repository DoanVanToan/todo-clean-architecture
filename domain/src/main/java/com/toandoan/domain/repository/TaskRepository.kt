package com.toandoan.domain.repository

import com.toandoan.domain.model.Task
import io.reactivex.Completable
import io.reactivex.Single

interface TaskRepository : Repository {
    fun getTasks(): Single<List<Task>>

    fun getTasks(query: String): Single<List<Task>>

    fun insertTask(title: String, isDone: Boolean = false): Single<Task>

    fun isExistTask(title: String): Single<Boolean>

    fun deleteTasks(): Completable
}