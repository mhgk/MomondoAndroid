package dk.momondo.momondolight.flightsearch.model


data class Journey(
        val offer: Offer,
        val ticketClass: TicketClass,
        val flight: Flight,
        val legsMapFromTo: HashMap<Int, List<Leg>>)