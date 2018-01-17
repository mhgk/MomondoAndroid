package dk.momondo.momondolight.flightsearch.model

class SearchResult(var originName: String,
                   var originCode: String,
                   var destinationName: String,
                   var destinationCode: String,
                   var airlines: List<Airline>,
                   var airports: List<Airport>,
                   var ticketClasses: List<TicketClass>,
                   var flights: List<Flight>,
                   var legs: List<Leg>,
                   var segments: List<Segment>,
                   var suppliers: List<Supplier>,
                   var offers: List<Offer>? = null
)
