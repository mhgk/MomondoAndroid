package dk.momondo.momondolight.frontpage.viewmodel

import android.arch.lifecycle.ViewModel
import dk.momondo.momondolight.flightsearch.DateUtils
import dk.momondo.momondolight.flightsearch.model.FlightQuery
import dk.momondo.momondolight.flightsearch.repository.FlightRepository
import dk.momondo.momondolight.flightsearch.viewmodel.JourneyAdapterViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class FlightSearchViewModel : ViewModel() {


    fun searchForFlights(flightQuery: FlightQuery): Single<ArrayList<JourneyAdapterViewModel>> {

        return FlightRepository
                .searchForFlights(flightQuery)
                .map { result ->
                    arrayListOf<JourneyAdapterViewModel>()
                            .run {
                                result
                                        .forEach {
                                            this.add(JourneyAdapterViewModel(it, DateUtils()))
                                        }
                                this
                            }
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

}