package com.example.mvvmex.ui.creteTodo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.mvvmex.R
import com.example.mvvmex.data.db.TodoTable
import com.example.mvvmex.ui.BaseActivity
import com.example.mvvmex.utils.Constants
import kotlinx.android.synthetic.main.activity_create_todo.*

class CreateTodoActivity : BaseActivity() {

    private var todoRecord: TodoTable? = null

    companion object {
        fun startForResult(activity: Activity, todoTable: TodoTable) {
            val intent = Intent(activity, CreateTodoActivity::class.java)
            intent.putExtra(Constants.INTENT_TODO, todoTable)
            activity.startActivityForResult(intent, Constants.REQUEST_INTENT_ADD_TODO)
        }
    }


    override fun defineLayoutResource(): Int {
        return R.layout.activity_create_todo
    }

    override fun initializeBehavior() {
        if (intent.extras != null && intent.hasExtra(Constants.INTENT_TODO)) {
            todoRecord = intent.getParcelableExtra(Constants.INTENT_TODO)!!
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
        val isNotEmpty = validateFields(
                arrayOf<EditText>(
                        activity_create_todo_etName,
                        activity_create_todo_etContent
                )
        )

        if (isNotEmpty){

        }
    }
}
