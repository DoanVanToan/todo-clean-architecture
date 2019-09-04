package com.toandoan.cleanarchitechture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.toandoan.cleanarchitechture.base.AppScheduler
import com.toandoan.cleanarchitechture.base.BaseViewModel
import com.toandoan.cleanarchitechture.base.DataWrapper
import com.toandoan.cleanarchitechture.model.TaskItem
import com.toandoan.cleanarchitechture.model.TaskItemMapper
import com.toandoan.domain.usecase.task.CreateTaskUseCase
import com.toandoan.domain.usecase.task.DeleteAllTaskUseCase
import com.toandoan.domain.usecase.task.DeleteTaskUseCase
import com.toandoan.domain.usecase.task.GetTaskUseCase

class TaskViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val deleteAllTaskUseCase: DeleteAllTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val mapper: TaskItemMapper,
    private val scheduler: AppScheduler
) : BaseViewModel() {
    // LiveData to show all tasks in database
    private val _tasks = MutableLiveData<DataWrapper<MutableList<TaskItem>>>()
    val tasks: LiveData<DataWrapper<MutableList<TaskItem>>>
        get() = _tasks

    // LiveData to show recently added task
    private val _addedTask = MutableLiveData<DataWrapper<TaskItem>>()
    val addedTask: LiveData<DataWrapper<TaskItem>>
        get() = _addedTask

    // Delete task event callback, true for successful and false for error
    private val _isDeleteAllTasksSucessful = MutableLiveData<DataWrapper<Boolean>>()
    val isDeleteAllTaskSuccesfull: LiveData<DataWrapper<Boolean>>
        get() = _isDeleteAllTasksSucessful

    /**
     * Trigger to annouce user for delete one Task success or error.
     */
    private val _isDeleteTaskSuccessful = MutableLiveData<DataWrapper<Boolean>>()
    val isDeleteTaskSuccess: LiveData<DataWrapper<Boolean>>
        get() = _isDeleteTaskSuccessful

    init {
        loadTasks()
    }

    private fun loadTasks() {
        val disposable = getTaskUseCase
            .execute()
            .doOnSubscribe { _tasks.postValue(DataWrapper.loading()) }
            .flatMapIterable { it }
            .map { mapper.mapToPresentation(it) }
            .toList()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.androidMainThread())
            .subscribe({ tasks ->
                _tasks.postValue(DataWrapper.success(tasks))
            }, {
                _tasks.postValue(DataWrapper.error(it.toError()))
            })
        compositeDisposable.add(disposable)
    }

    fun addTask(title: String) {
        val disposable = createTaskUseCase
            .execute(CreateTaskUseCase.Parram(title))
            .doOnSubscribe { _addedTask.postValue(DataWrapper.loading()) }
            .map { mapper.mapToPresentation(it) }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.androidMainThread())
            .subscribe({ task ->
                _addedTask.postValue(DataWrapper.success(task))
                _tasks.value = tasks.value?.apply {
                    data?.add(task)
                }
            }, {
                _addedTask.postValue(DataWrapper.error(it.toError()))
            })

        compositeDisposable.add(disposable)
    }

    fun deleteAllTasks() {
        val disposable = deleteAllTaskUseCase
            .execute()
            .doOnSubscribe { _isDeleteAllTasksSucessful.postValue(DataWrapper.loading()) }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.androidMainThread())
            .subscribe({
                _isDeleteAllTasksSucessful.postValue(DataWrapper.success(it))
                _tasks.value = DataWrapper.success(arrayListOf())
            }, {
                _isDeleteAllTasksSucessful.postValue(DataWrapper.error(it.toError()))
            })
        compositeDisposable.add(disposable)
    }

    fun deleteTask(taskItem: TaskItem) {
        val task = mapper.mapToDomain(taskItem)
        val disposable = deleteTaskUseCase
            .execute(task)
            .doOnSubscribe { }
            .subscribe({ isSuccess ->
                if (isSuccess) {
                    _isDeleteTaskSuccessful.value = DataWrapper.success(isSuccess)
                    _tasks.value = tasks.value?.apply {
                        data?.remove(taskItem)
                    }
                }
            }, {
                _isDeleteTaskSuccessful.value = DataWrapper.error(it.toError())
            })
        compositeDisposable.add(disposable)
    }
}

fun Throwable.toError(): Error {
    return Error(this)
}