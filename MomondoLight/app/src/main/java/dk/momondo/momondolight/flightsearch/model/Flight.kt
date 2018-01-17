package dk.momondo.momondolight.flightsearch.model

import java.time.Duration

data class Flight(var id: Int, val ticketClassIndex: Int, val segmentIndices: List<Int>)