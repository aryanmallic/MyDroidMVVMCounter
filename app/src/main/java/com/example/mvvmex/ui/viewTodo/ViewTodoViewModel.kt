package com.example.mvvmex.ui.viewTodo

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.mvvmex.data.db.TodoRepository
import com.example.mvvmex.data.db.TodoTable
import com.example.mvvmex.utils.UiLifeCycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Akhtar on 08/06/20
 */
class ViewTodoViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    private val todoRepository = TodoRepository(application)

    private val uiScope = UiLifeCycleScope(Dispatchers.IO)

    companion object {
        private val TAG = ViewTodoViewModel::class.java.simpleName
    }

    suspend fun saveTodo(todo: TodoTable) {
        todoRepository.saveTodo(todo)
    }

    suspend fun updateTodo(todo: TodoTable) {
        todoRepository.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: TodoTable) {
        todoRepository.deleteTodo(todo)
    }

    fun getAllTodoList(): LiveData<List<TodoTable>> {
        return todoRepository.getAllTodoList()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onCreate() {
        Log.d(TAG, "Lifecycle : uiScope created in onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "Lifecycle : uiScope destroyed in onPause")
    }

    interface ViewTodo{
        fun onAddTodo(v: View)
    }


}