package dk.momondo.momondolight.flightsearch.repository

import dk.momondo.momondolight.flightsearch.FlightApiCreator
import dk.momondo.momondolight.flightsearch.model.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


object FlightRepository {

    private val flightApi = FlightApiCreator
            .createApi()

    //
//    Observable
//    .range(0, it.size - 1)
//    .flatMap(
//    {
//        v ->
//        Timber.d("count: %s", v)
//        val date = DateTime.now()
//
//        fetchEventsForRoom(
//                calendarService,
//                it[v].email,
//                date)
//                .toObservable()
//    })
//    .toList()
    fun searchForFlights(flightQuery: FlightQuery): Single<List<Journey>>
            = flightApi
            .searchForFlights(flightQuery)
            .flatMap { result ->
                Observable
                        .range(0, result.offers?.lastIndex ?: 0)
                        .flatMap { i ->
                            Observable
                                    .create<Journey> {
                                        createJourney(result.offers!![i], result)
                                                .takeIf { it != null }
                                                ?.run { it.onNext(this) }
                                        it.onComplete()
                                    }
                                    .subscribeOn(Schedulers.computation())
                        }
                        .toList()
            }


    private fun createJourney(offer: Offer, searchResult: SearchResult): Journey? {
        Timber.d("Thread %s", Thread.currentThread().name)

        val flight = searchResult
                .flights
                .find { flight -> flight.id == offer.flightIndex } ?: return null

        val segmentIndices = flight
                .segmentIndices

        return Journey(
                offer,
                searchResult.ticketClasses[offer.ticketClassIndex],
                flight,
                hashMapOf<Int, List<Leg>>()
                        .run {
                            segmentIndices
                                    .forEachIndexed { index, i ->
                                        this[index] = getLegsForSegment(
                                                searchResult.segments[i].legIndices,
                                                searchResult
                                        )
                                    }
                            this
                        })
    }

    private fun getLegsForSegment(legIndices: List<Int>, searchResult: SearchResult): List<Leg> =
            arrayListOf<Leg>()
                    .run {
                        legIndices
                                .forEach { i ->
                                    searchResult
                                            .legs
                                            .find { it.id == i }
                                            .takeIf { it != null }
                                            ?.apply {
                                                airline = searchResult.airlines.find { airline -> airline.id == airlineIndex } ?: Airline()
                                                destinationAirport = searchResult.airports.find { airport -> airport.id == destinationIndex } ?: Airport()
                                                originAirport = searchResult.airports.find { airport -> airport.id == originIndex } ?: Airport()
                                            }
                                            ?.let {
                                                this.add(it)
                                            }
                                }
                        this
                    }
}