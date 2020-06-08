package com.example.mvvmex.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Akhtar on 08/06/20
 */
@Dao
interface TodoDao {
    @Query("SELECT * FROM todo ORDER BY id DESC")
    fun getAllTodoList(): LiveData<List<TodoTable>>

    @Insert
    fun saveTodo(todoRecord: TodoTable)

    @Delete
    fun deleteTodo(todoRecord: TodoTable)

    @Update
    fun updateTodo(todoRecord: TodoTable)
}