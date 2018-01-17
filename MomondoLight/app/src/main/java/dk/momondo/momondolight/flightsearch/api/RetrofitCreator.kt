package dk.momondo.momondolight.flightsearch.api

import dk.momondo.momondolight.flightsearch.model.FlightQuery
import dk.momondo.momondolight.flightsearch.model.SearchResult
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


object FlightApiCreator {

    fun createApi() = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl("http://momondo-interview.herokuapp.com/")
            .build()
            .create(MomondoApi::class.java)

    interface MomondoApi {

        @Headers(
                "Content-Type: application/json;charset=UTF-8"
        )
        @POST("search")
        fun searchForFlights(
                @Body flightQuery: FlightQuery): Single<SearchResult>
    }
}