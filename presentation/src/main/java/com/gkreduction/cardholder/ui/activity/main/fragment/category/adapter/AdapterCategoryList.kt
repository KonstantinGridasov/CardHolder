package com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter

import android.annotation.SuppressLint
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.ItemCategoryBinding
import com.gkreduction.cardholder.utils.getDefaultCategoryName
import com.gkreduction.cardholder.utils.showKeyboard
import com.gkreduction.domain.entity.Category
import java.util.*


class AdapterCategoryList(
    private val listener: CategoryAdapterClickListener?,
    private val onChangeListener: OnChangePositionItemListener?,
    private val items: List<Category>,
) :
    RecyclerView.Adapter<AdapterCategoryList.ViewHolder>(), DragItemTouchHelper.MoveHelperAdapter {

    private var chooses: Long = -1L
    private var mDragStartListener: OnStartDragListener? = null

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder?)
    }


    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_category, parent, false)
        return ViewHolder(binding)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.isModeEdit = false
        holder.binding.etCategoryName.text = items[position].catName
        holder.binding.ivTrash.setOnClickListener {
            listener?.removeCategory(items[position])
        }

        holder.binding.ivEdit.setOnClickListener {
            listener?.editCategory(items[position])
        }

        holder.binding.isDefaultCat =
            (items[position].catName == getDefaultCategoryName(holder.itemView.context))


        holder.binding.clickItemView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if ((items[position].catId) != chooses) {
                    chooses = items[position].catId
                    listener?.onChoose(items[position])
                }
                deactivateAllItems()
                mDragStartListener?.onStartDrag(holder)
            }
            false
        }
        if ((items[position].isActive)) {
            activate(holder)
        } else {
            deactivate(holder)
        }
        holder.binding.executePendingBindings()

    }

    override fun getItemCount() = items.size


    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun movedFinish() {
        onChangeListener?.onChange(items)
    }

    fun setDragListener(dragStartListener: OnStartDragListener) {
        this.mDragStartListener = dragStartListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setActiveCategory(category: Category?) {
        this.chooses = category?.catId ?: -1L
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun deactivateAllItems() {
        items.forEach {
            it.isActive = it.catId == chooses
        }
        notifyDataSetChanged()

    }

    private fun deactivate(holder: ViewHolder) {
        holder.itemView.setBackgroundResource(R.drawable.category_not_active)
    }

    private fun activate(holder: ViewHolder) {
        holder.itemView.setBackgroundResource(R.drawable.category_active)
    }

}


