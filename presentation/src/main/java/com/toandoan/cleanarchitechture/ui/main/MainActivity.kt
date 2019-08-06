package com.toandoan.cleanarchitechture.ui.main

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toandoan.cleanarchitechture.R
import com.toandoan.cleanarchitechture.base.Status
import com.toandoan.cleanarchitechture.enity.TaskItem
import com.toandoan.cleanarchitechture.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: TaskViewModel by viewModel()

    lateinit var taskAdapter: TaskAdapter
    private val dialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()
        initListener()
    }

    private fun initListener() {
        findViewById<Button>(R.id.buttonCreateTask).setOnClickListener {
            val title = findViewById<EditText>(R.id.editTaskName).text.toString()
            viewModel.addTask(title)
        }
    }

    private fun observeTasks() {
        viewModel.tasks.observe(this@MainActivity, Observer {
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
        viewModel.addedTask.observe(this@MainActivity, Observer {
            when (it.status) {
                Status.LOADING -> showLoadingIndicator()
                Status.SUCCESS -> {
                    hideLoadingIndicator()
                    displayAddedTask(it.data!!)
                }
                Status.ERROR -> {
                    hideLoadingIndicator()
                    showError(it.error)
                }
            }
        })
    }

    private fun observeDeletedTask() {
        viewModel.isDeleteAllTaskSuccesfull.observe(this@MainActivity, Observer {
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
                            taskAdapter.clear()
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

    private fun displayAddedTask(addedTask: TaskItem) {
        taskAdapter.addTask(addedTask)
    }

    private fun displayTasks(tasks: List<TaskItem>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_tasks)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            taskAdapter = TaskAdapter(tasks.toMutableList()) { item ->
                toast(item.title)
            }
            adapter = taskAdapter
        }
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

