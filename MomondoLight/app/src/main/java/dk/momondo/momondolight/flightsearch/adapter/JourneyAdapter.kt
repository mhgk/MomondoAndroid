package dk.momondo.momondolight.flightsearch.adapter

import android.databinding.DataBindingUtil
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dk.momondo.momondolight.R
import dk.momondo.momondolight.databinding.JourneyAdapterItemBinding
import dk.momondo.momondolight.flightsearch.viewmodel.JourneyAdapterViewModel
import java.util.*


class JourneyAdapter : RecyclerView.Adapter<JourneyAdapter.ViewHolder>() {

    private var itemsList: List<JourneyAdapterViewModel>

    init {
        itemsList = ArrayList()
        setHasStableIds(true)
    }

    fun updateItems(items: List<JourneyAdapterViewModel>) {

        val isEmpty = itemsList.isEmpty()

        itemsList = items

        if (isEmpty) {
            notifyItemRangeInserted(0, itemsList.size)
        } else {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindToItem(position)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.journey_adapter_item
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun getItemId(position: Int): Long {
        return itemsList[position].journey.offer.id.toLong()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: JourneyAdapterItemBinding = DataBindingUtil.bind(itemView)

        init {
            if (adapterPosition != NO_POSITION) {
                binding
                        .card
                        .setOnClickListener {
                            Snackbar
                                    .make(it, "TODO", Snackbar.LENGTH_SHORT)
                                    .show()
                        }
            }
        }

        fun bindToItem(position: Int) {
            binding
                    .viewModel = itemsList[position]
        }
    }
}