package com.toandoan.domain.usecase

interface UseCase<in P, out T : Any> {
    fun execute(parram: P): T
}

interface WithoutParramUseCase<out T : Any> {
    fun execute(): T
}