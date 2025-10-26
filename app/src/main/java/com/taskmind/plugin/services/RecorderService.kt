package com.taskmind.plugin.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Build
import android.os.IBinder
import android.provider.CalendarContract
import androidx.core.app.NotificationCompat
import com.taskmind.plugin.db.AppDatabase
import com.taskmind.plugin.db.LogEvent
import com.taskmind.plugin.receivers.BatteryReceiver
import com.taskmind.plugin.receivers.WifiReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * A background service that records user context and system events.
 * This service runs in the foreground to ensure it is not killed by the system.
 * It listens for battery changes, Wi-Fi state changes, and reads calendar events.
 */
class RecorderService : Service() {

    private val db by lazy { AppDatabase.getDatabase(this) }
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private lateinit var batteryReceiver: BatteryReceiver
    private lateinit var wifiReceiver: WifiReceiver

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        logEvent("RecorderService started")

        batteryReceiver = BatteryReceiver { batteryLevel ->
            logEvent("Battery level changed", "Battery: $batteryLevel%")
        }
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        wifiReceiver = WifiReceiver { wifiState ->
            logEvent("Wi-Fi state changed", wifiState)
        }
        registerReceiver(wifiReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))

        readCalendarEvents()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        logEvent("RecorderService stopped")
        unregisterReceiver(batteryReceiver)
        unregisterReceiver(wifiReceiver)
    }

    /**
     * Logs an event to the local database.
     * @param event The name of the event.
     * @param context Additional context for the event.
     * @param battery The current battery level.
     * @param calendarEvent A description of a calendar event.
     */
    private fun logEvent(event: String, context: String? = null, battery: Int? = null, calendarEvent: String? = null) {
        serviceScope.launch {
            val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val log = LogEvent(
                time = currentTime,
                context = context,
                event = event,
                battery = battery,
                weather = null, // TODO: Get weather
                calendarEvent = calendarEvent
            )
            db.logEventDao().insert(log)
        }
    }

    /**
     * Reads upcoming calendar events and logs them to the database.
     * Requires the READ_CALENDAR permission.
     */
    @SuppressLint("Range")
    private fun readCalendarEvents() {
        serviceScope.launch {
            val projection = arrayOf(CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART)
            val selection = "${CalendarContract.Events.DTSTART} >= ?"
            val selectionArgs = arrayOf(System.currentTimeMillis().toString())
            val sortOrder = "${CalendarContract.Events.DTSTART} ASC"

            try {
                contentResolver.query(
                    CalendarContract.Events.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
                )?.use { cursor ->
                    while (cursor.moveToNext()) {
                        val title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE))
                        val startDate = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART))
                        val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(startDate))
                        logEvent("Calendar Event", calendarEvent = "$title at $formattedDate")
                    }
                }
            } catch (e: SecurityException) {
                logEvent("Error reading calendar", "Permission denied")
            }
        }
    }

    private fun startForegroundService() {
        val channelId = "recorder_service_channel"
        val channelName = "TaskMind Recorder Service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("TaskMind")
            .setContentText("Recording user context...")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with a proper icon
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
