package com.toandoan.cleanarchitechture.ui.taskdetail

import android.os.Bundle
import com.toandoan.cleanarchitechture.R
import com.toandoan.cleanarchitechture.base.BaseActivity
import com.toandoan.cleanarchitechture.model.TaskItem

class TaskDetailActivity : BaseActivity() {

    private val taskItem: TaskItem by lazy {
        TaskDetailArgs.deserilizeable(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
    }

}
