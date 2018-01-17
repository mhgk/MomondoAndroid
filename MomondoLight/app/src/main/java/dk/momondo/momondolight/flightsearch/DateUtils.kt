package dk.momondo.momondolight.flightsearch

import org.joda.time.Period
import org.joda.time.format.DateTimeFormatterBuilder


class DateUtils {

    private val formatter = DateTimeFormatterBuilder()
            .appendPattern("HH:mm")
            .toFormatter()

    private val dateFormatter = DateTimeFormatterBuilder()
            .appendDayOfMonth(1)
            .appendMonthOfYearShortText()
            .toFormatter()

    fun formatMillisToHoursAndMinutes(date: Long) =
            formatter
                    .print(date)

    fun getPeriod(from: Long, to: Long) =
            Period(from, to)

    fun formatMilLisToDayAndMonth(date: Long): String =
            dateFormatter
                    .print(date)


}