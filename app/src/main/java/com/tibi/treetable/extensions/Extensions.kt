package com.tibi.treetable.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatDate(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(calendar.time)
}