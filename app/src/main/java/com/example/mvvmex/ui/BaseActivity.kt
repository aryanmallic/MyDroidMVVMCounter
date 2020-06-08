package com.example.mvvmex.ui

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmex.R
import com.example.mvvmex.utils.UiUtils

/**
 * Created by Akhtar on 08/06/20
 */

abstract class BaseActivity : AppCompatActivity(){

    private var mLastClickTime: Long = 0

    protected abstract fun defineLayoutResource(): Int

    protected abstract fun initializeBehavior()

    companion object{
        const val CLICK_MIN_INTERVAL: Long = 700
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(defineLayoutResource())
        initializeBehavior()
    }

    fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isSingleClick(): Boolean {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime

        return elapsedTime > CLICK_MIN_INTERVAL
    }

    fun validateFields(fields: Array<EditText>): Boolean {
        for (i in fields.indices) {
            val currentField = fields[i]
            if (currentField.text.toString().trim().isEmpty()) {
                currentField.requestFocus()
                UiUtils.showToast(this, getString(R.string.msg_fields_empty))
                return false
            }
        }
        return true
    }
}