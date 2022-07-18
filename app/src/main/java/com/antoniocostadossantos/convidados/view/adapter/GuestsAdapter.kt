package com.antoniocostadossantos.convidados.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.convidados.databinding.RowGuestBinding
import com.antoniocostadossantos.convidados.model.GuestModel
import com.antoniocostadossantos.convidados.view.listener.OnGuestListener
import com.antoniocostadossantos.convidados.view.viewHolder.GuestsViewHolder

class GuestsAdapter : RecyclerView.Adapter<GuestsViewHolder>() {
    private lateinit var listener: OnGuestListener
    private var guestsList: List<GuestModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestsViewHolder {
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestsViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: GuestsViewHolder, position: Int) {
        holder.bind(guestsList[position])
    }

    override fun getItemCount(): Int {
        return guestsList.count()
    }

    fun updatedGuests(list: List<GuestModel>){
        guestsList = list
        notifyDataSetChanged()
    }

    fun attachListener(guestListener: OnGuestListener){
        listener = guestListener
    }
}