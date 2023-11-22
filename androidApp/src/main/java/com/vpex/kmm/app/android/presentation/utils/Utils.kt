package com.vpex.kmm.app.android.presentation.utils

import android.annotation.SuppressLint
import java.sql.Date
import java.text.SimpleDateFormat

object Utils {
    @SuppressLint("SimpleDateFormat")
    fun getDateTime(s: String): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}