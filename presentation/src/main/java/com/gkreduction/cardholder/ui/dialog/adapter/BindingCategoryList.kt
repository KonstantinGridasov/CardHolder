package com.gkreduction.cardholder.ui.dialog.adapter

import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.domain.entity.Category


object BindingCategoryList {
    @JvmStatic
    @BindingAdapter(
        "list_items",
        "item_chooses",
        "list_items_click",
        requireAll = false
    )
    fun setListChatsAdapter(
        view: RecyclerView,
        items: List<Category>?,
        category: Category?,
        listener: CategoryClickListener?
    ) {
        items?.let {
            val adapter = AdapterCategoryList(listener)
            adapter.updateItems(it)
            adapter.setActiveCategory(category)
            view.adapter = adapter
        }

    }

    @JvmStatic
    @BindingAdapter(
        "edit_listeners",
        requireAll = false
    )
    fun setEditListener(
        view: EditText,
        listener: EditTextListener?
    ) {
        view.setImeActionLabel(view.text.toString(), KeyEvent.KEYCODE_ENTER)
        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    listener?.onEnterSet(view.text.toString())
                    view.setText("")
                    return true
                }
                return false
            }
        })

    }

}