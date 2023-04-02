package com.gkreduction.cardholder.ui.activity.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.ItemCategoryBinding
import com.gkreduction.cardholder.ui.dialog.adapter.CategoryClickListener
import com.gkreduction.cardholder.utils.getDefaultCategoryName
import com.gkreduction.domain.entity.Category

class AdapterCategoryMain(val listener: CategoryClickListener?) :
    RecyclerView.Adapter<AdapterCategoryMain.ViewHolder>() {
    private var items: List<Category> = ArrayList()
    private var chooses: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_category, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCategoryName.text = items[position].catName
        holder.binding.tvCategoryName.setOnClickListener {
            if ((items[position].catName) == chooses) {
                deactivate(holder)
                listener?.onItemClick(null)
            } else {
                chooses = items[position].catName
                activate(holder)
                listener?.onItemClick(items[position])
            }
        }
        if ((items[position].catName) == chooses) {
            activate(holder)
        } else {
            deactivate(holder)
        }
        holder.binding.isDefaultCat =
            (items[position].catName == getDefaultCategoryName(holder.itemView.context))

        holder.binding.executePendingBindings()

    }

    override fun getItemCount() = items.size


    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setActiveCategory(category: Category?) {
        this.chooses = category?.catName ?: ""
        notifyDataSetChanged()
    }


    private fun deactivate(holder: ViewHolder) {
//        holder.binding.ivChooser.visibility = View.INVISIBLE

    }

    private fun activate(holder: ViewHolder) {
//        holder.binding.ivChooser.visibility = View.VISIBLE
    }


}


