package com.example.mvvmex.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.SystemClock
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mvvmex.R
import com.example.mvvmex.utils.UiLifeCycleScope
import com.example.mvvmex.utils.UiUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by Akhtar on 08/06/20
 */

abstract class BaseActivity : AppCompatActivity() {

    private var mLastClickTime: Long = 0

    protected abstract fun initializeBindingComponent(binding: ViewDataBinding)

    protected abstract fun defineLayoutResource(): Int

    protected abstract fun initializeBehavior()

    private lateinit var job:Job

    companion object {
        const val CLICK_MIN_INTERVAL: Long = 700
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(defineLayoutResource())
        val binding: ViewDataBinding  = DataBindingUtil.setContentView(this, defineLayoutResource())
        initializeBindingComponent(binding)
        job = Job()
        initializeBehavior()
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
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


    //Function to close keyboard in case touch except input box
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        val handleReturn = super.dispatchTouchEvent(ev)

        val view = currentFocus

        val x = ev.x.toInt()
        val y = ev.y.toInt()

        if (view is EditText) {
            val innerView = currentFocus

            if (ev.action == MotionEvent.ACTION_UP && !getLocationOnScreen(innerView as EditText).contains(
                            x,
                            y
                    )
            ) {

                val input = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                input.hideSoftInputFromWindow(
                        window.currentFocus!!
                                .windowToken, 0
                )
            }
        }

        return handleReturn
    }

    private fun getLocationOnScreen(mEditText: EditText): Rect {
        val mRect = Rect()
        val location = IntArray(2)
        mEditText.getLocationOnScreen(location)

        mRect.left = location[0]
        mRect.top = location[1]
        mRect.right = location[0] + mEditText.getWidth()
        mRect.bottom = location[1] + mEditText.getHeight()

        return mRect
    }
}