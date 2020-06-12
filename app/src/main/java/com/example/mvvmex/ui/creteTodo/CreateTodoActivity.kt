package com.example.mvvmex.ui.creteTodo

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmex.R
import com.example.mvvmex.data.db.TodoTable
import com.example.mvvmex.ui.BaseActivity
import com.example.mvvmex.utils.Constants
import com.example.mvvmex.utils.UiUtils
import kotlinx.android.synthetic.main.activity_create_todo.*


class CreateTodoActivity : BaseActivity() {

    private lateinit var createTodoViewModel: CreateTodoViewModel

    private var todoRecord: TodoTable? = null

    companion object {
        fun startForResult(activity: Activity, todoTable: TodoTable?) {
            val intent = Intent(activity, CreateTodoActivity::class.java)
            intent.putExtra(Constants.INTENT_TODO, todoTable)
            activity.startActivityForResult(intent, Constants.REQUEST_INTENT_ADD_TODO)
        }

        fun startForResult(activity: Activity) {
            val intent = Intent(activity, CreateTodoActivity::class.java)
            activity.startActivityForResult(intent, Constants.REQUEST_INTENT_ADD_TODO)
        }
    }


    override fun defineLayoutResource(): Int {
        return R.layout.activity_create_todo
    }

    override fun initializeBehavior() {
        createTodoViewModel = ViewModelProvider(this).get(CreateTodoViewModel::class.java)
        lifecycle.addObserver(createTodoViewModel)

        if (intent.extras != null && intent.hasExtra(Constants.INTENT_TODO)) {
            todoRecord = intent.getParcelableExtra(Constants.INTENT_TODO)
            updateUi()
        }

        if (todoRecord != null) {
            activity_create_todo_btSave.text = getString(R.string.label_update)
        } else {
            activity_create_todo_btSave.text = getString(R.string.label_save)
        }
    }

    private fun updateUi() {
        activity_create_todo_etName.setText(todoRecord?.title)
        activity_create_todo_etContent.setText(todoRecord?.content)
    }

    fun onSave(v: View) {
        val name = activity_create_todo_etName.text.toString().trim()
        val content = activity_create_todo_etContent.text.toString().trim()

        val isNotEmpty = validateFields(
                arrayOf<EditText>(
                        activity_create_todo_etName,
                        activity_create_todo_etContent
                )
        )

        if (isNotEmpty) {
            if (activity_create_todo_btSave.text == getString(R.string.label_save)) {
                createTodoViewModel.saveTodo(TodoTable(null, name, content))
            }else{
                createTodoViewModel.updateTodo(TodoTable(todoRecord?.id, name, content))
            }

            returnIntent()
        }
    }

    fun returnIntent() {
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
