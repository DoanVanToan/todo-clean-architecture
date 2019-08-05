package com.toandoan.domain.usecase.task

import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import com.toandoan.domain.usecase.UseCase

class SearchTaskUseCase constructor(
    private val taskRepository: TaskRepository
) : UseCase<String, List<Task>> {

    /**
     * if parram is null or empty get all task
     * if parram is not null or empty search task by name
     */
    override fun execute(parram: String): List<Task> {
        if (parram.isEmpty()) {
            return taskRepository.getTasks()
        }
        return taskRepository.getTasks(parram)
    }

}