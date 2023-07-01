package com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.ItemCategoryBinding
import com.gkreduction.cardholder.utils.getDefaultCategoryName
import com.gkreduction.domain.entity.Category
import java.util.*


class AdapterCategoryList(val listener: CategoryAdapterClickListener?) :
    RecyclerView.Adapter<AdapterCategoryList.ViewHolder>(), DragItemTouchHelper.MoveHelperAdapter {
    private var items: List<Category> = ArrayList()
    private var chooses: Long = -1L
    private var mDragStartListener: OnStartDragListener? = null

    private enum class ModeCategory {
        EDIT,
        CREATE
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder?)
    }

    fun setDragListener(dragStartListener: OnStartDragListener) {
        this.mDragStartListener = dragStartListener
    }

    private var mode: ModeCategory = ModeCategory.CREATE


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_category, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)


    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.isModeEdit = false
        holder.binding.etCategoryName.setText(items[position].catName)
        holder.binding.ivTrash.setOnClickListener {
            listener?.removeCategory(items[position])
        }

        holder.binding.ivEdit.setOnClickListener {
            mode = ModeCategory.EDIT
            setModeEditable(holder, items[position])
        }

        holder.binding.isDefaultCat =
            (items[position].catName == getDefaultCategoryName(holder.itemView.context))

        if (items[position].catId == 0L) {
            setModeEditable(holder, items[position])
        } else
            setModeDefault(holder)

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

    private fun setModeDefault(holder: ViewHolder) {
        holder.binding.etCategoryName.isEnabled = false
        holder.binding.clickItemView.visibility = View.VISIBLE
        holder.binding.isModeEdit = false
    }

    private fun setModeEditable(holder: ViewHolder, category: Category) {
        holder.binding.isModeEdit = true
        holder.binding.clickItemView.visibility = View.GONE
        holder.binding.etCategoryName.isEnabled = true
        val imm = holder.itemView.context?.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.showSoftInput(holder.binding.etCategoryName, InputMethodManager.SHOW_IMPLICIT)

        holder.binding.etCategoryName.isFocusableInTouchMode = true
        holder.binding.etCategoryName.requestFocus()
        holder.binding.etCategoryName.setImeActionLabel(
            holder.binding.etCategoryName.text.toString(),
            KeyEvent.KEYCODE_ENTER
        )
        holder.binding.etCategoryName.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (mode == ModeCategory.CREATE)
                        listener?.addCategory(holder.binding.etCategoryName.text.toString())
                    else {
                        createNewCategory(holder.binding.etCategoryName.text.toString(), category)
                        mode = ModeCategory.CREATE
                    }
                    setModeDefault(holder)
                    return true
                }
                return false
            }
        })
    }

    private fun createNewCategory(text: String, category: Category) {
        listener?.updateCategory(
            Category(
                catId = category.catId,
                catName = text, position = category.position
            )
        )
    }

    override fun getItemCount() = items.size


    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
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

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }


}


