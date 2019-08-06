package com.toandoan.domain.usecase.task

import com.toandoan.domain.repository.TaskRepository
import com.toandoan.domain.usecase.WithoutParramUseCase
import io.reactivex.Completable
import io.reactivex.Observable

class DeleteAllTaskUseCase(
    private val repository: TaskRepository
) : WithoutParramUseCase<Boolean> {
    override fun execute(): Observable<Boolean> {
        return repository.deleteTasks().toObservableBoolean()
    }

}

fun Completable.toObservableBoolean(): Observable<Boolean> {
    return toSingleDefault(true)
        .onErrorReturnItem(false)
        .toObservable()
}