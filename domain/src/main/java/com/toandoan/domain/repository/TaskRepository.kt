package com.toandoan.domain.repository

import com.toandoan.domain.model.Task

interface TaskRepository : Repository {
    fun getTasks(): List<Task>

    fun getTasks(query: String): List<Task>

    fun insertTask(title: String, isDone: Boolean = false): Task

    fun isExistTask(title: String): Boolean
}