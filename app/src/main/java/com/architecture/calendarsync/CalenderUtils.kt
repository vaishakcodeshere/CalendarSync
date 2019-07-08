@file:Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)

package com.architecture.calendarsync

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import java.util.*

object CalenderUtils {


    fun insertEvent(
        context: Context,
        startMillis: Long,
        endMillis: Long,
        calID: Int,
        title: String,
        description: String,
        reminder: Int
    ): String {

        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(CalendarContract.Events.TITLE, title)
            put(CalendarContract.Events.DESCRIPTION, description)
            put(CalendarContract.Events.CALENDAR_ID, 1)
            put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles")
        }
        val uri: Uri = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
        val eventID: Long = uri.lastPathSegment.toLong()


        Log.d("CALENDERINSERT", eventID.toString())

        val reminder_values = ContentValues().apply {
            put(CalendarContract.Reminders.MINUTES, reminder)
            put(CalendarContract.Reminders.EVENT_ID, eventID)
            put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        }
        val reminder_uri: Uri? = context.contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminder_values)

        /**
         * The events will be added to the google calendar inorder to sync the calender this function is called.
         */
        SyncCalendar.requestCalendarSync(context)


        return eventID.toString()

    }


    fun getMillis(year: Int, month: Int, day: Int, hour: Int, minutes: Int): Long {

        /**
         * this returns mills from the given time , date
         */
        return Calendar.getInstance().run {
            set(year, month, day, hour, minutes)
            timeInMillis
        }

    }

}