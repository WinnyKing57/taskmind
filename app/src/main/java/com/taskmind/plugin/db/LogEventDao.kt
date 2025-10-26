package com.taskmind.plugin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogEventDao {

    @Insert
    suspend fun insert(logEvent: LogEvent)

    @Query("SELECT * FROM log_events ORDER BY time DESC")
    fun getAllLogEvents(): Flow<List<LogEvent>>
}
