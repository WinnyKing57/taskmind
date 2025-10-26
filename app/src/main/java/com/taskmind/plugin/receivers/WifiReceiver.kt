package com.taskmind.plugin.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager

class WifiReceiver(private val onWifiStateChanged: (String) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == WifiManager.WIFI_STATE_CHANGED_ACTION) {
            when (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)) {
                WifiManager.WIFI_STATE_ENABLED -> onWifiStateChanged("Wi-Fi Enabled")
                WifiManager.WIFI_STATE_DISABLED -> onWifiStateChanged("Wi-Fi Disabled")
            }
        }
    }
}
