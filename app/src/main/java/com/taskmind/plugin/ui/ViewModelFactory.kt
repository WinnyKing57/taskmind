package com.taskmind.plugin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taskmind.plugin.db.LogEventDao

class ViewModelFactory(private val logEventDao: LogEventDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PredictionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PredictionsViewModel(logEventDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
