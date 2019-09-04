package com.toandoan.domain.usecase.task

import com.toandoan.domain.repository.TaskRepository
import com.toandoan.domain.usecase.WithoutParramUseCase
import io.reactivex.Completable
import io.reactivex.Observable

interface DeleteAllTaskUseCase : WithoutParramUseCase<Boolean>

class DeleteAllTaskUseCaseImpl(
    private val repository: TaskRepository
) : DeleteAllTaskUseCase {
    override fun execute(): Observable<Boolean> {
        return repository.deleteTasks().toObservableBoolean()
    }
}

fun Completable.toObservableBoolean(): Observable<Boolean> {
    return toSingleDefault(true)
        .onErrorReturnItem(false)
        .toObservable()
}