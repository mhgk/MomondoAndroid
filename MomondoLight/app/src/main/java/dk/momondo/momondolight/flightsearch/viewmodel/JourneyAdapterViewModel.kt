package dk.momondo.momondolight.flightsearch.viewmodel

import dk.momondo.momondolight.flightsearch.DateUtils
import dk.momondo.momondolight.flightsearch.model.Journey
import java.util.*


class JourneyAdapterViewModel(val journey: Journey, private val dateUtils: DateUtils) {

    private var flightDepartureTimeOrigin = ""
    private var flightArrivalTimeDestination = ""
    private var flightDepartureTimeDestination = ""
    private var flightArrivalTimeOrigin = ""
    private var originAirport = ""
    private var destinationAirport = ""
    private var flightTimeOut = ""
    private var flightTimeReturn = ""
    var airLines = ""
    var price = ""
    var ticketType = ""

    init {

        val awayLegs = journey.legsMapFromTo[0]
        val returnLegs = journey.legsMapFromTo[1]

        if (awayLegs != null && returnLegs != null) {

            flightDepartureTimeOrigin = dateUtils.formatMillisToHoursAndMinutes(awayLegs[0].departure)
            flightArrivalTimeDestination = dateUtils.formatMillisToHoursAndMinutes(awayLegs[awayLegs.lastIndex].arrival)
            flightDepartureTimeDestination = dateUtils.formatMillisToHoursAndMinutes(returnLegs[0].departure)
            flightArrivalTimeOrigin = dateUtils.formatMillisToHoursAndMinutes(returnLegs[returnLegs.lastIndex].departure)

            originAirport = awayLegs[0]
                    .originAirport
                    .name
                    .substringAfter("(")
                    .substring(0, 3)

            destinationAirport = awayLegs[awayLegs.lastIndex]
                    .destinationAirport
                    .name
                    .substringAfter("(")
                    .substring(0, 3)

            flightTimeOut = awayLegs
                    .run {
                        val period = dateUtils.getPeriod(this[0].departure, this[this.lastIndex].arrival)
                        String.format("%st %sm", period.hours, period.minutes)
                    }

            flightTimeReturn = returnLegs
                    .run {
                        val period = dateUtils.getPeriod(this[0].departure, this[this.lastIndex].arrival)
                        kotlin.String.format("%st %sm", period.hours, period.minutes)
                    }

            airLines = if (awayLegs[0].airline.name == returnLegs[0].airline.name)
                awayLegs[0].airline.name
            else
                String.format("%s, %s", awayLegs[0].airline.name, returnLegs[0].airline.name)
        }

        price = String.format(Locale.getDefault(),"%,d DKK",(journey.offer.price.toInt()))

        ticketType = journey.ticketClass.name
    }

    fun flightArrivalTime(isReturnFlight: Boolean) = if (isReturnFlight) flightArrivalTimeOrigin else flightArrivalTimeDestination
    fun flightDepartureTime(isReturnFlight: Boolean) = if (isReturnFlight) flightDepartureTimeDestination else flightDepartureTimeOrigin
    fun getArrivalAirport(isReturnFlight: Boolean) = if (isReturnFlight) originAirport else destinationAirport
    fun getDepartureAirport(isReturnFlight: Boolean) = if (isReturnFlight) destinationAirport else originAirport
    fun getFlightTime(isReturnFlight: Boolean) = if (isReturnFlight) flightTimeReturn else flightTimeOut

}