package com.toandoan.cleanarchitechture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toandoan.cleanarchitechture.enity.TaskItem
import com.toandoan.cleanarchitechture.enity.TaskItemMapper
import com.toandoan.domain.usecase.task.CreateTaskUseCase
import com.toandoan.domain.usecase.task.GetTaskUseCase

class TaskViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val mapper: TaskItemMapper
) : ViewModel() {

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
        val taskUseCase = getTaskUseCase
            .execute()
            .map { mapper.mapToPresentation(it) }
        _tasks.postValue(taskUseCase)
    }

    fun addTask(title: String) {
        try {
            val addedTaskUseCase = createTaskUseCase
                .execute(CreateTaskUseCase.Parram(title))
            _addedTask.postValue(mapper.mapToPresentation(addedTaskUseCase))
        } catch (e: IllegalArgumentException) {

        } catch (e: CreateTaskUseCase.TitleExistExeption) {

        }
    }
}

class TaskViewModelFactory constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val mapper: TaskItemMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(getTaskUseCase, createTaskUseCase, mapper) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}