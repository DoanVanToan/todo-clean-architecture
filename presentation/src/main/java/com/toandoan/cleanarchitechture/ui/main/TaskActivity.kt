package com.toandoan.cleanarchitechture.ui.main

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toandoan.cleanarchitechture.R
import com.toandoan.cleanarchitechture.base.BaseActivity
import com.toandoan.cleanarchitechture.base.Status
import com.toandoan.cleanarchitechture.model.TaskItem
import com.toandoan.cleanarchitechture.ui.taskdetail.TaskDetailArgs
import com.toandoan.cleanarchitechture.utils.addTouchHelper
import com.toandoan.cleanarchitechture.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskActivity : BaseActivity(), TaskNavigator {

    private val viewModel: TaskViewModel by viewModel()
    private lateinit var taskAdapter: TaskAdapter

    private val dialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecylerView()
        observeViewModel()
        initListener()
    }

    override fun openTaskDetail(task: TaskItem) {
        TaskDetailArgs(task).launch(this)
    }

    private fun initListener() {
        findViewById<Button>(R.id.buttonCreateTask).setOnClickListener {
            val title = findViewById<EditText>(R.id.editTaskName).text.toString()
            viewModel.addTask(title)
        }
    }

    private fun observeTasks() {
        viewModel.tasks.observe(this@TaskActivity, Observer {
            when (it.status) {
                Status.LOADING -> showLoadingIndicator()
                Status.SUCCESS -> {
                    hideLoadingIndicator()
                    displayTasks(it.data!!)
                }
                Status.ERROR -> {
                    hideLoadingIndicator()
                    showError(it.error)
                }
            }
        })
    }

    private fun observeAddedTasks() {
        viewModel.addedTask.observe(this@TaskActivity, Observer {
            when (it.status) {
                Status.LOADING -> showLoadingIndicator()
                Status.SUCCESS -> {
                    hideLoadingIndicator()
                }
                Status.ERROR -> {
                    hideLoadingIndicator()
                    showError(it.error)
                }
            }
        })
    }

    private fun observeDeletedTask() {
        viewModel.isDeleteAllTaskSuccesfull.observe(this@TaskActivity, Observer {
            when (it.status) {
                Status.LOADING -> showLoadingIndicator()
                Status.ERROR -> {
                    hideLoadingIndicator()
                    showError(it.error)
                }
                Status.SUCCESS -> {
                    hideLoadingIndicator()
                    it.data?.let {
                        if (it) {
                            toast(R.string.msg_delete_all_tasks_success_full)
                        } else {
                            toast(R.string.error_undefine_delete_all_tasks_succesful)
                        }
                    }
                }

            }
        })
    }

    private fun observeViewModel() {
        observeTasks()
        observeAddedTasks()
        observeDeletedTask()
    }

    private fun showLoadingIndicator() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    private fun hideLoadingIndicator() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    private fun showError(error: Error?) {
        error?.localizedMessage?.let {
            toast(it)
        }
    }

    private fun initRecylerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_tasks)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@TaskActivity)
            taskAdapter = TaskAdapter { item ->
                openTaskDetail(item)
            }
            adapter = taskAdapter

            val callback = ItemTouchHelperCallback { position, _ ->
                viewModel.deleteTask(taskAdapter.getTask(position))
            }
            addTouchHelper(callback)
        }
    }

    private fun displayTasks(tasks: List<TaskItem>) {
        taskAdapter.updateData(tasks)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_delete -> viewModel.deleteAllTasks()
        }
        return super.onOptionsItemSelected(item)
    }

}


