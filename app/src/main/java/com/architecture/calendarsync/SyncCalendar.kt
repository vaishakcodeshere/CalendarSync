package com.architecture.calendarsync

import android.accounts.AccountManager
import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log

object SyncCalendar {
    private val TAG = "SyncCalendar"
    fun requestCalendarSync(context: Context) {
        val aM = AccountManager.get(context)
        val accounts = aM.accounts
        for (account in accounts) {
            val isSyncable = ContentResolver.getIsSyncable(account, CalendarContract.AUTHORITY)
            if (isSyncable > 0) {
                val extras = Bundle()
                extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true)
                ContentResolver.requestSync(accounts[0], CalendarContract.AUTHORITY, extras)
            }
        }
        syncCalendars(context)
    }

    private fun syncCalendars(context: Context) {
        val accounts = AccountManager.get(context).accounts
        val authority = CalendarContract.Calendars.CONTENT_URI.authority
        for (account in accounts) {
            val extras = Bundle()
            extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true)
            ContentResolver.requestSync(account, authority, extras)
        }
    }
}