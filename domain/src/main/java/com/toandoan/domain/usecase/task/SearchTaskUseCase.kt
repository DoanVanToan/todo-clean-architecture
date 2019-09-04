package com.toandoan.domain.usecase.task

import com.toandoan.domain.model.Task
import com.toandoan.domain.repository.TaskRepository
import com.toandoan.domain.usecase.UseCase
import io.reactivex.Observable
import io.reactivex.Single

interface SearchTaskUseCase : UseCase<String, List<Task>>

class SearchTaskUseCaseImpl constructor(
    private val taskRepository: TaskRepository
) : SearchTaskUseCase {

    /**
     * if parram is null or empty get all task
     * if parram is not null or empty search task by name
     */
    override fun execute(parram: String): Observable<List<Task>> {
        return Single.just(parram)
            .flatMap {
                if (it.isEmpty()) {
                    taskRepository.getTasks()
                } else {
                    taskRepository.getTasks(parram)
                }
            }
            .toObservable()
    }

}