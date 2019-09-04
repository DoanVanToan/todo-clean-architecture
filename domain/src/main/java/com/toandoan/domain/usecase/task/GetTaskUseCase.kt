package com.toandoan.domain.usecase.task

import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import com.toandoan.domain.usecase.WithoutParramUseCase

interface GetTaskUseCase : WithoutParramUseCase<List<Task>>

class GetTaskUseCaseImpl constructor(
    private val taskRepository: TaskRepository
) : GetTaskUseCase {

    override fun execute() = taskRepository.getTasks().toObservable()
}