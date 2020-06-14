package com.example.mvvmex.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by Akhtar
 */

class UiLifeCycleScope(private val dispatcher: CoroutineDispatcher) : CoroutineScope, LifecycleObserver {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + dispatcher

    fun onCreate() {
        job = Job()
    }

    fun destroy() {
        if (job.isActive) {
            job.cancel()
        }
    }
}