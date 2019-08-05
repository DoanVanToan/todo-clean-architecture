package com.toandoan.domain.usecase.task

import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import com.toandoan.domain.usecase.WithoutParramUseCase

class GetTaskUseCase constructor(
    private val taskRepository: TaskRepository
) : WithoutParramUseCase<List<Task>> {

    override fun execute(): List<Task> = taskRepository.getTasks()
}