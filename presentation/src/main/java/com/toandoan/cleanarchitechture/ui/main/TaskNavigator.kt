package com.toandoan.cleanarchitechture.ui.main

import com.toandoan.cleanarchitechture.model.TaskItem

interface TaskNavigator {
    fun openTaskDetail(task: TaskItem)
}