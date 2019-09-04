package com.toandoan.domain.usecase.task

import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import com.toandoan.domain.usecase.UseCase
import io.reactivex.Observable

interface DeleteTaskUseCase : UseCase<Task, Boolean>

class DeleteTaskUseCaseImpl constructor(
    private val repository: TaskRepository
) : DeleteTaskUseCase {
    override fun execute(parram: Task): Observable<Boolean> =
        repository.deleteTask(parram).toObservable()
}