package com.toandoan.cleanarchitechture.base

class DataWrapper<T> private constructor(
    val status: Status,
    val data: T?,
    val error: Error?
) {
    companion object {
        fun <T> success(data: T?): DataWrapper<T> {
            return DataWrapper(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Error?): DataWrapper<T> {
            return DataWrapper(Status.ERROR, null, error)
        }

        fun <T> loading(): DataWrapper<T> {
            return DataWrapper(Status.LOADING, null, null)
        }

    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}