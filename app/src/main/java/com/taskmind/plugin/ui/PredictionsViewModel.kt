package com.taskmind.plugin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmind.plugin.PredictionEngine
import com.taskmind.plugin.Suggestion
import com.taskmind.plugin.db.LogEventDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PredictionsViewModel(private val logEventDao: LogEventDao) : ViewModel() {

    private val predictionEngine = PredictionEngine()

    val suggestions: StateFlow<List<Suggestion>> = logEventDao.getAllLogEvents()
        .map { events -> predictionEngine.generateSuggestions(events) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
