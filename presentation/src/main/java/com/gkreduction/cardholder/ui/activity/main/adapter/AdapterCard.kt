package com.gkreduction.cardholder.ui.activity.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.ItemCardBinding
import com.gkreduction.domain.entity.Card

class AdapterCard :
    RecyclerView.Adapter<AdapterCard.ViewHolder>() {
    private var items: List<Card> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCardBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_card, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textHeader.text = items[position].cardName
        holder.binding.textDescription.text = items[position].cardBaseInfo
        holder.binding.cardItem.setCardBackgroundColor(items[position].color)
        holder.binding.barcode.scanCode = (items[position].primary)
        holder.binding.executePendingBindings()

    }

    override fun getItemCount() = items.size


    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Card>) {
        this.items = items
        notifyDataSetChanged()
    }
}


