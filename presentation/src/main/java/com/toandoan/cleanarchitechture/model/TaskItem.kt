package com.toandoan.cleanarchitechture.model

import android.os.Parcelable
import com.toandoan.cleanarchitechture.base.ItemMapper
import com.toandoan.domain.model.Task
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskItem(
    val id: Long,
    val title: String,
    val isDone: Boolean
) : ItemModel(), Parcelable

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