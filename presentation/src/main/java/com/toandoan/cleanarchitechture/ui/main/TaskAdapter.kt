package com.toandoan.cleanarchitechture.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.toandoan.cleanarchitechture.R
import com.toandoan.cleanarchitechture.base.BaseAdapter
import com.toandoan.cleanarchitechture.base.BaseViewHolder
import com.toandoan.cleanarchitechture.model.TaskItem

class TaskAdapter(
    private val onClick: ((TaskItem) -> Any)?
) : BaseAdapter<TaskItem, TaskAdapter.ViewHolder>(TaskDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_task, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    fun getTask(position: Int): TaskItem {
        return getItem(position)
    }

    fun updateData(tasks: List<TaskItem>) {
        submitList(mutableListOf<TaskItem>().apply { addAll(tasks) })
    }

    class ViewHolder constructor(
        view: View,
        onClick: ((TaskItem) -> Any)?
    ) : BaseViewHolder(view) {
        private lateinit var task: TaskItem
        private var textPosition: TextView
        private var textTaskname: TextView

        init {
            view.setOnClickListener {
                onClick?.let { callback ->
                    callback(task)
                }
            }
            textPosition = view.findViewById(R.id.textPosition)
            textTaskname = view.findViewById(R.id.textTaskName)
        }

        fun bindData(task: TaskItem) {
            this.task = task
            textPosition.text = "${adapterPosition + 1}"
            textTaskname.text = task.title
        }

    }

    class TaskDiffUtilCallback : DiffUtil.ItemCallback<TaskItem>() {
        override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem.title == newItem.title
        }
    }
}



