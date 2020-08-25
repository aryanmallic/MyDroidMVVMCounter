package com.example.mvvmex.ui.viewTodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmex.R
import com.example.mvvmex.data.db.TodoTable
import com.example.mvvmex.databinding.ActivityViewTodoBinding
import com.example.mvvmex.ui.BaseActivity
import com.example.mvvmex.ui.creteTodo.CreateTodoActivity
import com.example.mvvmex.utils.Constants
import kotlinx.android.synthetic.main.activity_view_todo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewTodoActivity : BaseActivity(), ViewTodoViewModel.ViewTodo {

    private lateinit var binding: ActivityViewTodoBinding

    private lateinit var viewTodoViewModel: ViewTodoViewModel

    private lateinit var viewTodoAdapter: ViewTodoAdapter

    companion object {
        val TAG = ViewTodoActivity::class.java.simpleName
    }

    override fun initializeBindingComponent(binding: ViewDataBinding) {
        this.binding = binding as ActivityViewTodoBinding
    }

    override fun defineLayoutResource(): Int {
        return R.layout.activity_view_todo
    }

    override fun initializeBehavior() {
        viewTodoViewModel = ViewModelProvider(this).get(ViewTodoViewModel::class.java)

        binding.eventHandler = this
        lifecycle.addObserver(viewTodoViewModel)

        initList()


        viewTodoViewModel.getAllTodoList().observe(this@ViewTodoActivity, Observer {
            viewTodoAdapter.updateList(it)
        })

    }

    private fun initList() {
        viewTodoAdapter = ViewTodoAdapter(object : ViewTodoAdapter.ViewTodoAdapterListener {
            override fun onClicked(todoTable: TodoTable, eventType: String) {
                if (eventType == Constants.EVENT_DELETE) {
                    suspend {
                        viewTodoViewModel.deleteTodo(todoTable)
                    }
                } else {
                    CreateTodoActivity.startForResult(this@ViewTodoActivity, todoTable)
                }
            }
        })
        binding.activityViewTodoRvList.adapter = viewTodoAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_INTENT_ADD_TODO) {
            //UiUtils.showToast(this, "New Entry")
        }
    }

    override fun onAddTodo(v: View) {
        CreateTodoActivity.startForResult(this)
    }
}
