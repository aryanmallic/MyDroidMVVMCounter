package com.example.mvvmex.utils

import android.app.Activity
import android.widget.Toast

/**
 * Created by Akhtar on 08/06/20
 */

object UiUtils {
    fun showToast(context: Activity?, message: String) {
        if (!context?.isFinishing!!)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}