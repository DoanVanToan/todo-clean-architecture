package com.toandoan.domain.usecase.task

import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import com.toandoan.domain.usecase.UseCase
import com.toandoan.domain.usecase.task.CreateTaskUseCase.ErrorMessage.TASK_EXIST
import com.toandoan.domain.usecase.task.CreateTaskUseCase.ErrorMessage.TITLE_EMPTY

class CreateTaskUseCase constructor(
    private val taskRepository: TaskRepository
) : UseCase<CreateTaskUseCase.Parram, Task> {
    /**
     * If title is empty throw exeption
     * If this task is exist, throw an exeption
     */
    override fun execute(parram: Parram): Task {
        if (parram.title.isEmpty()) {
            throw IllegalArgumentException(TITLE_EMPTY)
        }
        if (taskRepository.isExistTask(parram.title)) {
            throw TitleExistExeption(TASK_EXIST)
        }
        return taskRepository.insertTask(
            parram.title,
            parram.isDone
        )
    }


    class Parram(val title: String, val isDone: Boolean = false)

    class TitleExistExeption(message: String) : Exception(message)

    object ErrorMessage {
        const val TITLE_EMPTY = "Title must not be empty"
        const val TASK_EXIST = "Task is exist"
    }
}