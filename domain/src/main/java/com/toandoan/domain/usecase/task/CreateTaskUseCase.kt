package com.toandoan.domain.usecase.task

import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import com.toandoan.domain.usecase.UseCase
import com.toandoan.domain.usecase.task.CreateTaskUseCase.ErrorMessage.TASK_EXIST
import com.toandoan.domain.usecase.task.CreateTaskUseCase.ErrorMessage.TITLE_EMPTY
import io.reactivex.Observable
import io.reactivex.Single

class CreateTaskUseCase constructor(
    private val taskRepository: TaskRepository
) : UseCase<CreateTaskUseCase.Parram, Task> {

    /**
     * If title is empty throw exeption
     * If this task is exist, throw an exeption
     */
    override fun execute(parram: Parram): Observable<Task> {
        return Single.just(parram.title)
            .flatMap { title ->
                if (title.isEmpty()) {
                    Single.error(java.lang.IllegalArgumentException(TITLE_EMPTY))
                } else {
                    taskRepository.isExistTask(title)
                }
            }
            .flatMap { isExist ->
                if (isExist) {
                    Single.error(TitleExistExeption(TASK_EXIST))
                } else
                    taskRepository.insertTask(parram.title, parram.isDone)
            }
            .toObservable()
    }


    class Parram(val title: String, val isDone: Boolean = false)

    class TitleExistExeption(message: String) : Exception(message)

    object ErrorMessage {
        const val TITLE_EMPTY = "Title must not be empty"
        const val TASK_EXIST = "Task is exist"
    }
}