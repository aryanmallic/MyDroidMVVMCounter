package com.example.mvvmex.data.db

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mvvmex.utils.CoroutinesUtils

/**
 * Created by Akhtar on 08/06/20
 */

class TodoRepository(application: Application) {
    private val todoDao: TodoDao
    private val todoList: LiveData<List<TodoTable>>

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        todoDao = database!!.todoDao()
        todoList = todoDao.getAllTodoList()
    }

    fun getAllTodoList(): LiveData<List<TodoTable>> {
        return todoList
    }

    fun saveTodo(todo: TodoTable) {
        CoroutinesUtils.io {
            todoDao.saveTodo(todo)
        }
    }

    fun updateTodo(todo: TodoTable) {
        CoroutinesUtils.io {
            todoDao.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: TodoTable) {
        CoroutinesUtils.io {
            todoDao.deleteTodo(todo)
        }
    }

}