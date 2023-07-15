package com.abubakar.notesapp.data.util

import javax.inject.Inject

internal class TimeProvider @Inject constructor() {
    fun getCurrentTime() = System.currentTimeMillis()
}