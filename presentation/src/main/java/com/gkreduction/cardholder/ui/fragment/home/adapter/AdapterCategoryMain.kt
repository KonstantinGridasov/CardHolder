package com.gkreduction.cardholder.ui.fragment.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.ItemMainCategoryBinding
import com.gkreduction.cardholder.ui.fragment.home.CategoryClickListener
import com.gkreduction.cardholder.utils.getColorByStatus
import com.gkreduction.domain.entity.Category

class AdapterCategoryMain(val listener: CategoryClickListener?) :
    RecyclerView.Adapter<AdapterCategoryMain.ViewHolder>() {
    private var items: List<Category> = ArrayList()
    private var chooses: Category? = null
    private var oldPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemMainCategoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_main_category, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemMainCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.textMainCategory.text = items[position].catName
        holder.binding.rootCategoriesMain.setOnClickListener {
            if ((items[position]) != chooses) {
                chooses = items[position]
                notifyItemChanged(oldPosition)
                oldPosition = position
                activate(holder)
                listener?.onItemClick(items[position])
            }
        }
        if ((items[position]) == chooses) {
            activate(holder)
        } else {
            deactivate(holder)
        }

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
        this.chooses = category
        notifyDataSetChanged()
    }


    private fun deactivate(holder: ViewHolder) {
        holder.binding.rootCategoriesMain.isSelected = false
        holder.binding.textMainCategory.setTextColor(getColorByStatus(false, holder.itemView))

    }

    private fun activate(holder: ViewHolder) {
        holder.binding.rootCategoriesMain.isSelected = true
        holder.binding.textMainCategory.setTextColor(getColorByStatus(true, holder.itemView))


    }


}


