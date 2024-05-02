package com.w36495.randomrithm.utils

object TimeFormat {
    private const val TIME_FORMAT = "%02d"
    private const val TIME_DELIMITER = " : "
    private const val TIME_DELIMITER_HH = "시간"
    private const val TIME_DELIMITER_MM = "분"
    private const val TIME_DELIMITER_SS = "초"

    fun formattedTimeToDisplay(time: Int): String {
        val sb = StringBuilder()
            .append(formatHours(time))
            .append(TIME_DELIMITER)
            .append(formatMinutes(time))
            .append(TIME_DELIMITER)
            .append(formatSecond(time))

        return sb.toString()
    }

    fun formattedTimeToDisplayKr(time: Int): String {
        val sb = StringBuilder()
            .append(formatHours(time))
            .append(TIME_DELIMITER_HH)
            .append(formatMinutes(time))
            .append(TIME_DELIMITER_MM)
            .append(formatSecond(time))
            .append(TIME_DELIMITER_SS)

        return sb.toString()
    }

    private fun formatHours(time: Int): String = String.format(TIME_FORMAT, time / 3600)
    private fun formatMinutes(time: Int): String = String.format(TIME_FORMAT, (time % 3600) / 60)
    private fun formatSecond(time: Int): String = String.format(TIME_FORMAT, (time % 3600) % 60)
}