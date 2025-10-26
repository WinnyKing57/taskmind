package com.taskmind.plugin.tasker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TaskerEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // For now, we'll just finish the activity immediately
        // In the future, this is where the user would configure the event
        val resultIntent = Intent()
        // Add any configuration data to the intent here
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
