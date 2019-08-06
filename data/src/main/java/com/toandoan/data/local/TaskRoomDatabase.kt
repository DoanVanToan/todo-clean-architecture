package com.toandoan.data.local

import android.content.Context
import androidx.room.*
import com.toandoan.data.local.TaskDatabase.Companion.VERSION
import com.toandoan.data.model.TaskEnity
import com.toandoan.data.model.TaskEntry

@Dao
interface TaskDAO {
    @Query("SELECT * FROM ${TaskEntry.TABLE_NAME}")
    fun getTasks(): List<TaskEnity>

    @Query("SELECT * FROM ${TaskEntry.TABLE_NAME} WHERE ${TaskEntry.TITLE} LIKE :title")
    fun getTasks(title: String): List<TaskEnity>

    @Query("SELECT COUNT(${TaskEntry.ID}) FROM ${TaskEntry.TABLE_NAME} WHERE ${TaskEntry.TITLE} = :title ")
    fun countTask(title: String): Int

    @Insert
    fun insertTask(task: TaskEnity)

    @Query("DELETE FROM ${TaskEntry.TABLE_NAME}")
    fun deleteTasks()
}

@Database(entities = arrayOf(TaskEnity::class), version = VERSION)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDAO(): TaskDAO

    companion object {
        const val VERSION = 1
        const val DATABASE_NAME = "task.db"
        fun getInstance(context: Context) = Room
            .databaseBuilder(
                context,
                TaskDatabase::class.java,
                DATABASE_NAME
            )
            .allowMainThreadQueries()
            .build()
    }
}