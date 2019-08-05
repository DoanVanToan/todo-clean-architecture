package com.toandoan.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toandoan.domain.model.Task

@Entity(tableName = TaskEntry.TABLE_NAME)
data class TaskEnity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TaskEntry.ID) val id: Long,
    @ColumnInfo(name = TaskEntry.TITLE) val title: String,
    @ColumnInfo(name = TaskEntry.IS_DONE) val isDone: Boolean = false
) : EnityModel()

class TaskEnityMapper : EnityMapper<Task, TaskEnity>() {
    override fun mapToEnity(domainModel: Task): TaskEnity {
        return TaskEnity(
            id = domainModel.id,
            title = domainModel.title,
            isDone = domainModel.isDone
        )
    }

    override fun mapToDomain(enityModel: TaskEnity): Task {
        return Task(
            id = enityModel.id,
            title = enityModel.title,
            isDone = enityModel.isDone
        )
    }

}

object TaskEntry {
    const val TABLE_NAME = "tbl_task"
    const val ID = "tbl_task"
    const val TITLE = "title"
    const val IS_DONE = "is_done"
}