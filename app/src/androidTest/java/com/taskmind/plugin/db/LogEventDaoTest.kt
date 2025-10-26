package com.taskmind.plugin.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogEventDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var logEventDao: LogEventDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        logEventDao = database.logEventDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetLogEvent() = runBlocking {
        val logEvent = LogEvent(time = "1", event = "Test Event", context = null, battery = null, weather = null, calendarEvent = null)
        logEventDao.insert(logEvent)
        val allEvents = logEventDao.getAllLogEvents().first()
        assertEquals(allEvents[0], logEvent)
    }
}
