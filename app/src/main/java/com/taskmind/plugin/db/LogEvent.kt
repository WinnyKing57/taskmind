package com.taskmind.plugin.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log_events")
data class LogEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: String,
    val context: String?,
    val event: String,
    val battery: Int?,
    val weather: String?,
    val calendarEvent: String?
)
