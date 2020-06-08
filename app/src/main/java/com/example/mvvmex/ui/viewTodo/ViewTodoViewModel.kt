package com.example.mvvmex.ui.viewTodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.example.mvvmex.data.db.TodoRepository
import com.example.mvvmex.data.db.TodoTable
import com.example.mvvmex.ui.creteTodo.CreateTodoViewModel

/**
 * Created by Akhtar on 08/06/20
 */
class ViewTodoViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    private val todoRepository = TodoRepository(application)

    companion object {
        private val TAG = CreateTodoViewModel::class.java.simpleName
    }

    fun saveTodo(todo: TodoTable) {
        todoRepository. saveTodo(todo)
    }

    fun updateTodo(todo: TodoTable) {
        todoRepository.updateTodo(todo)
    }

    fun deleteTodo(todo: TodoTable) {
        todoRepository.deleteTodo(todo)
    }

    fun getAllTodoList(): LiveData<List<TodoTable>> {
        return todoRepository.getAllTodoList()
    }
}