package dk.momondo.momondolight.flightsearch

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import dk.momondo.momondolight.R
import dk.momondo.momondolight.databinding.ActivityFlightSearchBinding
import dk.momondo.momondolight.flightsearch.adapter.JourneyAdapter
import dk.momondo.momondolight.flightsearch.model.FlightQuery
import dk.momondo.momondolight.flightsearch.viewmodel.QueryViewModel
import dk.momondo.momondolight.frontpage.viewmodel.FlightSearchViewModel
import timber.log.Timber

class FlightSearchActivity : AppCompatActivity() {

    private lateinit var viewModel: FlightSearchViewModel
    private lateinit var binding: ActivityFlightSearchBinding
    private val journeyAdapter = JourneyAdapter()
    private val isLoading = ObservableBoolean(true)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_flight_search)


        viewModel = ViewModelProviders
                .of(this)
                .get(FlightSearchViewModel::class.java)

        binding.isLoading = isLoading

        binding
                .recyclerView
                .apply {
                    setHasFixedSize(true)
                    adapter = journeyAdapter
                }
    }

    private fun setFlightQuery(flightQuery: FlightQuery) {
        TransitionManager.beginDelayedTransition(binding.headerView)
        binding
                .flightQueryModel = QueryViewModel(
                flightQuery,
                DateUtils())
    }

    private fun toogleIsLoading(loading: Boolean) {
        TransitionManager.beginDelayedTransition(binding.headerView)
        isLoading.set(loading)
    }

    override fun onResume() {
        super.onResume()

        val flightQuery = FlightQuery(
                "CPH",
                "PAR",
                1516316400000,
                1517180400000,
                1
        )

        setFlightQuery(flightQuery)

        viewModel
                .searchForFlights(
                        flightQuery)
                .subscribe({ result ->
                    Timber.d("Flights found %s", result.size)
                    toogleIsLoading(false)
                    journeyAdapter.updateItems(result)
                }, {
                    toogleIsLoading(false)
                    isLoading.set(false)
                    Timber.d(it, "Failed searching for flights")
                })
    }
}