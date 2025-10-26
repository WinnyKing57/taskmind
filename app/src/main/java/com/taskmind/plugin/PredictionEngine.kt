package com.taskmind.plugin

import com.taskmind.plugin.db.LogEvent

data class Suggestion(
    val action: String,
    val confidence: Double
)

class PredictionEngine {

    fun generateSuggestions(logEvents: List<LogEvent>): List<Suggestion> {
        if (logEvents.isEmpty()) {
            return emptyList()
        }

        // Simple algorithm: Find the most frequent event
        val eventCounts = logEvents.groupingBy { it.event }.eachCount()
        val mostFrequentEvent = eventCounts.maxByOrNull { it.value }?.key

        return mostFrequentEvent?.let {
            listOf(Suggestion(action = "Do '$it' again?", confidence = 0.8))
        } ?: emptyList()
    }
}
