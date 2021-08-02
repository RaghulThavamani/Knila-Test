package com.example.editor.Utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

public object Coroutine {

    fun main(work : suspend (() -> Unit)) = CoroutineScope(Dispatchers.IO).launch {
        work()
    }
}