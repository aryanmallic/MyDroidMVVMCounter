package com.example.mvvmex.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

object CoroutinesUtils {

    fun main(work: suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.Main).launch {
                work()
            }

    fun io(work: suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.IO).launch {
                work()
            }

    fun main(job: Job, work: suspend (() -> Unit)): Job =
            CoroutineScope(job + Dispatchers.Main).launch {
                work()
            }

    fun io(job: Job, work: suspend (() -> Unit)): Job =
            CoroutineScope(job + Dispatchers.IO).launch {
                work()
            }
}