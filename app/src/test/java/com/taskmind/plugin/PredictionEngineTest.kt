package com.taskmind.plugin

import com.taskmind.plugin.db.LogEvent
import org.junit.Assert.assertEquals
import org.junit.Test

class PredictionEngineTest {

    private val predictionEngine = PredictionEngine()

    @Test
    fun `generateSuggestions returns empty list when logEvents is empty`() {
        val suggestions = predictionEngine.generateSuggestions(emptyList())
        assertEquals(0, suggestions.size)
    }

    @Test
    fun `generateSuggestions returns suggestion based on most frequent event`() {
        val logEvents = listOf(
            LogEvent(time = "1", event = "A", context = null, battery = null, weather = null, calendarEvent = null),
            LogEvent(time = "2", event = "B", context = null, battery = null, weather = null, calendarEvent = null),
            LogEvent(time = "3", event = "A", context = null, battery = null, weather = null, calendarEvent = null)
        )
        val suggestions = predictionEngine.generateSuggestions(logEvents)
        assertEquals(1, suggestions.size)
        assertEquals("Do 'A' again?", suggestions[0].action)
    }
}
