package com.toandoan.cleanarchitechture.enity

import com.toandoan.cleanarchitechture.base.ItemMapper
import com.toandoan.domain.model.Task

data class TaskItem(
    val id: Long,
    val title: String,
    val isDone: Boolean
) : ItemModel()

class TaskItemMapper : ItemMapper<Task, TaskItem> {
    override fun mapToDomain(itemModel: TaskItem): Task {
        return Task(
            id = itemModel.id,
            title = itemModel.title,
            isDone = itemModel.isDone
        )
    }

    override fun mapToPresentation(domainModel: Task): TaskItem {
        return TaskItem(
            id = domainModel.id,
            title = domainModel.title,
            isDone = domainModel.isDone
        )
    }

}