/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 * Sponsored by RW MobiMedia UK Limited
 *
 */

package com.rwmobi.dazncodechallenge.ui.utils

import android.content.Context
import android.text.format.DateUtils
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.parseTimeStamp(dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss"): Date {
    val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
    return simpleDateFormat.parse(this)!!
}

fun Date.asNiceString(context: Context): String {
    Timber.v("asNiceString: date = $time")
    // Data formatting similar but not exactly the same as the mockups.
    // This is done without using any 3rd party library
    return DateUtils.getRelativeDateTimeString(
        context,
        time,
        DateUtils.DAY_IN_MILLIS,
        DateUtils.DAY_IN_MILLIS * 3,
        0,
    ).toString()
}