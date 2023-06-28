package com.gkreduction.cardholder.ui.fragment.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.ItemMainCardBinding
import com.gkreduction.cardholder.ui.activity.main.adapter.CardClickListener
import com.gkreduction.domain.entity.Card

class AdapterCard(var listener: CardClickListener?) :
    RecyclerView.Adapter<AdapterCard.ViewHolder>() {
    private var items: List<Card> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemMainCardBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_main_card, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemMainCardBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textMainCard.text = items[position].cardName
        holder.binding.rootCard.setBackgroundColor(items[position].colorStart)
        holder.binding.rootCard.setOnClickListener {
            listener?.onItemClick(items[position].cardId)
        }
        holder.binding.executePendingBindings()

    }

    override fun getItemCount() = items.size


    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Card>) {
        this.items = items
        notifyDataSetChanged()
    }
}


