package com.toandoan.cleanarchitechture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toandoan.cleanarchitechture.base.AppScheduler
import com.toandoan.cleanarchitechture.base.DataWrapper
import com.toandoan.cleanarchitechture.enity.TaskItem
import com.toandoan.cleanarchitechture.enity.TaskItemMapper
import com.toandoan.domain.usecase.task.CreateTaskUseCase
import com.toandoan.domain.usecase.task.DeleteAllTaskUseCase
import com.toandoan.domain.usecase.task.GetTaskUseCase
import io.reactivex.disposables.CompositeDisposable

class TaskViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val deleteAllTaskUseCase: DeleteAllTaskUseCase,
    private val mapper: TaskItemMapper,
    private val scheduler: AppScheduler
) : ViewModel() {
    // Composite disposable to manage disposable avoid memory leak
    private val compositeDisposable = CompositeDisposable()

    // LiveData to show all tasks in database
    private val _tasks = MutableLiveData<DataWrapper<List<TaskItem>>>()
    val tasks: LiveData<DataWrapper<List<TaskItem>>>
        get() = _tasks

    // LiveData to show recently added task
    private val _addedTask = MutableLiveData<DataWrapper<TaskItem>>()
    val addedTask: LiveData<DataWrapper<TaskItem>>
        get() = _addedTask

    // Delete task event callback, true for successful and false for error
    private val _isDeleteAllTasksSucessful = MutableLiveData<DataWrapper<Boolean>>()
    val isDeleteAllTaskSuccesfull: LiveData<DataWrapper<Boolean>>
        get() = _isDeleteAllTasksSucessful

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
            }, {
                _isDeleteAllTasksSucessful.postValue(DataWrapper.error(it.toError()))
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

class TaskViewModelFactory constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val deleteAllTaskUseCase: DeleteAllTaskUseCase,
    private val mapper: TaskItemMapper,
    private val scheduler: AppScheduler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(getTaskUseCase, createTaskUseCase, deleteAllTaskUseCase, mapper, scheduler) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}

fun Throwable.toError(): Error {
    return Error(this)
}