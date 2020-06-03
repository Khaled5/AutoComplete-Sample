package com.easyinc.autocomplete.utils

import android.util.Log

object Logger {

    private const val TAG = "AutoComplete"

    fun dt(value: String) {
        Log.d(TAG, "Thread Name: ${Thread.currentThread().name} - $value")
    }
}