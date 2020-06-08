package com.example.mvvmex.ui.viewTodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmex.R
import com.example.mvvmex.data.db.TodoTable
import com.example.mvvmex.ui.creteTodo.CreateTodoActivity
import com.example.mvvmex.ui.creteTodo.CreateTodoViewModel
import com.example.mvvmex.utils.Constants
import kotlinx.android.synthetic.main.activity_view_todo.*

class ViewTodoActivity : AppCompatActivity() {

    private lateinit var viewTodoViewModel: ViewTodoViewModel

    private lateinit var viewTodoAdapter: ViewTodoAdapter

    companion object {
        val TAG = ViewTodoActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_todo)

        viewTodoViewModel = ViewModelProvider(this).get(ViewTodoViewModel::class.java)
        lifecycle.addObserver(viewTodoViewModel)

        initList()

        viewTodoViewModel.getAllTodoList().observe(this, Observer {
            Toast.makeText(this, "" + it.size, Toast.LENGTH_SHORT).show()
            viewTodoAdapter.updateList(it)
        })
    }


    private fun initList() {
        viewTodoAdapter = ViewTodoAdapter(object : ViewTodoAdapter.ViewTodoAdapterListener {
            override fun onClicked(todoTable: TodoTable, eventType: String) {
                if (eventType == Constants.EVENT_DELETE) {
                    viewTodoViewModel.deleteTodo(todoTable)
                } else {
                    CreateTodoActivity.startForResult(this@ViewTodoActivity)
                }
            }
        })
        activity_view_todo_rvList.adapter = viewTodoAdapter
    }
}
