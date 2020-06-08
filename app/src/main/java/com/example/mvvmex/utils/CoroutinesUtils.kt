package com.example.mvvmex.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CoroutinesUtils {
    fun main(work: suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.Main).launch {
                work()
            }

    fun io(work: suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.IO).launch {
                work()
            }
}