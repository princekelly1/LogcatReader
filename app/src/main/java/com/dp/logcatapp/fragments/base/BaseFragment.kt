package com.dp.logcatapp.fragments.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {
    protected lateinit var handler: Handler
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    protected fun runOnUIThread(runnable: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            runnable()
        } else {
            handler.post(runnable)
        }
    }
}
