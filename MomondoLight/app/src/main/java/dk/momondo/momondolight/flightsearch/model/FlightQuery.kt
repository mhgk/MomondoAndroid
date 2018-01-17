package dk.momondo.momondolight.flightsearch.model


data class FlightQuery(
        val origin: String,
        val destination: String,
        val departureDate: Long,
        val returnDate: Long,
        val ticketCount: Int)