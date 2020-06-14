package com.example.mvvmex.ui.creteTodo

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mvvmex.data.db.TodoRepository
import com.example.mvvmex.data.db.TodoTable
import com.example.mvvmex.utils.UiLifeCycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Akhtar on 08/06/20
 */
class CreateTodoViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    private val todoRepository = TodoRepository(application)

    private val uiScope = UiLifeCycleScope(Dispatchers.IO)

    companion object {
        private val TAG = CreateTodoViewModel::class.java.simpleName
    }

    fun saveTodo(todo: TodoTable) {
        uiScope.launch {
            todoRepository.saveTodo(todo)
        }
    }

    fun updateTodo(todo: TodoTable) {
        uiScope.launch {
            todoRepository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: TodoTable) {
        uiScope.launch {
            todoRepository.deleteTodo(todo)
        }
    }

    fun getAllTodoList(): LiveData<List<TodoTable>> {
        return todoRepository.getAllTodoList()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onCreate() {
        uiScope.onCreate()
        Log.d(TAG, "Lifecycle : uiScope created in onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        uiScope.destroy()
        Log.d(TAG, "Lifecycle : uiScope destroyed in onPause")
    }
}