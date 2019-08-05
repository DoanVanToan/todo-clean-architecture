package com.toandoan.domain.usecase

import io.reactivex.Observable

interface UseCase<in P, T : Any> {
    fun execute(parram: P): Observable<T>
}

interface WithoutParramUseCase<T : Any> {
    fun execute(): Observable<T>
}