# TaskMind Plugin

TaskMind is an intelligent Tasker plugin that observes, records, and analyzes user behavior to provide predictive suggestions.

## Features

*   **Contextual Timeline:** Automatically logs system events, calendar entries, and Tasker actions.
*   **Manual Actions:** Allows users to schedule their own actions.
*   **Predictive Suggestions:** Generates suggestions based on user behavior patterns.
*   **Tasker Integration:** Seamlessly communicates with Tasker for automation.

## Getting Started

### Prerequisites

*   Android 8.0 (API 26) or higher
*   Tasker app installed

### Installation

1.  Build the project in Android Studio.
2.  Install the generated APK on your Android device.

### Usage

1.  Open Tasker and create a new profile.
2.  Select the "Event" context.
3.  Choose "Plugin" -> "TaskMind Event".
4.  Configure the event as needed.
5.  Create a new task and add an action.
6.  Select "Plugin" -> "TaskMind Action".
7.  Configure the action as needed.

## Permissions

This plugin requires the following permissions:

*   `RECEIVE_BOOT_COMPLETED`
*   `ACCESS_FINE_LOCATION` (optional)
*   `FOREGROUND_SERVICE`
*   `POST_NOTIFICATIONS`
*   `READ_CALENDAR`
*   `INTERNET` (optional)
*   `QUERY_ALL_PACKAGES` (optional)
