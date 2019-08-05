package com.toandoan.cleanarchitechture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toandoan.cleanarchitechture.base.AppScheduler
import com.toandoan.cleanarchitechture.enity.TaskItem
import com.toandoan.cleanarchitechture.enity.TaskItemMapper
import com.toandoan.domain.usecase.task.CreateTaskUseCase
import com.toandoan.domain.usecase.task.GetTaskUseCase
import io.reactivex.disposables.CompositeDisposable

class TaskViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val mapper: TaskItemMapper,
    private val scheduler: AppScheduler
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _tasks = MutableLiveData<List<TaskItem>>()
    val tasks: LiveData<List<TaskItem>>
        get() = _tasks

    private val _addedTask = MutableLiveData<TaskItem>()
    val addedTask: LiveData<TaskItem>
        get() = _addedTask

    init {
        loadTasks()
    }

    private fun loadTasks() {
        val disposable = getTaskUseCase
            .execute()
            .flatMapIterable { it }
            .map { mapper.mapToPresentation(it) }
            .toList()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.androidMainThread())
            .subscribe({ tasks ->
                _tasks.postValue(tasks)
            }, { error ->
                // handle later
            })
        compositeDisposable.add(disposable)
    }

    fun addTask(title: String) {
        val disposable = createTaskUseCase
            .execute(CreateTaskUseCase.Parram(title))
            .map { mapper.mapToPresentation(it) }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.androidMainThread())
            .subscribe({ task ->
                _addedTask.postValue(task)
            }, { error ->
                // handle later
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
    private val mapper: TaskItemMapper,
    private val scheduler: AppScheduler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(getTaskUseCase, createTaskUseCase, mapper, scheduler) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}