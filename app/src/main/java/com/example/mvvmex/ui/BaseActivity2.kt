package com.example.mvvmex.ui

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


/**
 * Created by Akhtar
 */

abstract  class BaseActivity2:AppCompatActivity(),CoroutineScope {

    private lateinit var job:Job

    private var mLastClickTime: Long = 0

    protected abstract fun defineLayoutResource(): Int

    protected abstract fun initializeBehavior()


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        job = Job()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }



    //Kotlin extension function to show toast
    fun showToast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
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