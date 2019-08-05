package com.toandoan.cleanarchitechture.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toandoan.cleanarchitechture.R
import com.toandoan.cleanarchitechture.enity.TaskItem

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: TaskViewModel
    lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Injection.inject(this)
        observeTasks()
        initListener()
    }

    private fun initListener() {
        findViewById<Button>(R.id.buttonCreateTask).setOnClickListener {
            val title = findViewById<EditText>(R.id.editTaskName).text.toString()
            viewModel.addTask(title)
        }
    }

    private fun observeTasks() {
        viewModel.tasks.observe(this@MainActivity, Observer { tasks ->
            displayTasks(tasks)
        })

        viewModel.addedTask.observe(this@MainActivity, Observer { addedTask ->
            displayAddedTask(addedTask)
        })
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


}

fun Context.toast(messsage: String) {
    Toast.makeText(
        this,
        messsage,
        Toast.LENGTH_LONG
    ).show()
}
