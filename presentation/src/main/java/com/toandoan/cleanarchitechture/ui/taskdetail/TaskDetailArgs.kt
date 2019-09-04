package com.toandoan.cleanarchitechture.ui.taskdetail

import android.content.Context
import android.content.Intent
import com.toandoan.cleanarchitechture.base.ActivityArgs
import com.toandoan.cleanarchitechture.model.TaskItem

class TaskDetailArgs(
    private val task: TaskItem
) : ActivityArgs {
    override fun intent(context: Context) = Intent(context, TaskDetailActivity::class.java).apply {
        putExtra(EXTRA_TASK, task)
    }

    companion object {
        const val EXTRA_TASK = "EXTRA_TASK"

        fun deserilizeable(intent: Intent): TaskItem {
            return intent.getParcelableExtra(EXTRA_TASK)
        }
    }
}