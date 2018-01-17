package dk.momondo.momondolight.flightsearch.model


data class Leg(var id: Int, var airlineIndex: Int, val flightNumber: Int, val departure: Long, val arrival: Long,
               val destinationIndex: Int, val originIndex: Int) {

    var originAirport = Airport()
    var destinationAirport = Airport()
    var airline = Airline()
}