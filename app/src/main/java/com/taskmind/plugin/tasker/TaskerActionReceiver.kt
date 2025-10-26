package com.taskmind.plugin.tasker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.taskmind.plugin.services.RecorderService

class TaskerActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Here you would parse the intent from Tasker and perform the action
        // For now, let's just log that the action was received.

        val logIntent = Intent(context, RecorderService::class.java)
        // You can add extras to the intent to log specific details
        // logIntent.putExtra("event", "Tasker Action Received")
        // context.startService(logIntent)
    }
}
