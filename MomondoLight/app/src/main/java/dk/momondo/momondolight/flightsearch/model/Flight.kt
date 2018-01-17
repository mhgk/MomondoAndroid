package dk.momondo.momondolight.flightsearch.model

data class Flight(
        var id: Int,
        val ticketClassIndex: Int,
        val segmentIndices: List<Int>)