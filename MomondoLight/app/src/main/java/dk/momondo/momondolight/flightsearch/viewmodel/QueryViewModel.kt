package dk.momondo.momondolight.flightsearch.viewmodel

import android.databinding.BaseObservable
import dk.momondo.momondolight.flightsearch.DateUtils
import dk.momondo.momondolight.flightsearch.model.FlightQuery


class QueryViewModel(flightQuery: FlightQuery, dateUtils: DateUtils) : BaseObservable() {

    private var flightQuery: FlightQuery? = null

    var origin = flightQuery.origin
    var destination = flightQuery.destination
    var departAndReturnText: String? = null

    init {
        this.flightQuery = flightQuery

        origin = flightQuery.origin
        destination = flightQuery.destination

        departAndReturnText = flightQuery
                .run {
                    String.format(
                            "%s - %s",
                            dateUtils.formatMilLisToDayAndMonth(this.departureDate),
                            dateUtils.formatMilLisToDayAndMonth(this.returnDate))
                }
    }
}