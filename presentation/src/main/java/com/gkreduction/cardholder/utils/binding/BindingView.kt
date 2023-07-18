package com.gkreduction.cardholder.utils.binding

import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.ui.activity.main.fragment.add.EditTextListener
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.AdapterCategoryList
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.CategoryAdapterClickListener
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.DragItemTouchHelper
import com.gkreduction.cardholder.ui.activity.main.fragment.category.adapter.OnChangePositionItemListener
import com.gkreduction.cardholder.ui.widjet.BarcodeView
import com.gkreduction.cardholder.utils.AppTextWatcher
import com.gkreduction.cardholder.utils.hideKeyboard
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.entity.ScanCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


object BindingView {
    @JvmStatic
    @BindingAdapter("set_card", "set_status_revert", requireAll = false)
    fun setScanViewByRevert(view: BarcodeView, card: Card?, status_revert: Boolean?) {
        card?.let { item ->
            if (status_revert != null && status_revert && item.existSecondary) {
                item.secondary?.let { view.scanCode = it }
            } else
                item.primary?.let { view.scanCode = it }

        }
    }


    @JvmStatic
    @BindingAdapter("set_barcode", requireAll = false)
    fun setScanView(view: BarcodeView, barcode: ScanCode?) {
        barcode?.let { view.scanCode = it }
    }


    @JvmStatic
    @BindingAdapter("edit_listeners", requireAll = false)
    fun setEditViewListener(view: EditText, listener: EditTextListener?) {
        val publishSubject: PublishSubject<String> = PublishSubject.create()
        listener?.onText(
            publishSubject
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        )

        view.addTextChangedListener(AppTextWatcher {
            it?.let { publishSubject.onNext(it.toString()) }
        })

        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    hideKeyboard(view)
                    return true
                }
                return false
            }
        })
    }


    @JvmStatic
    @BindingAdapter(
        "list_items",
        "item_chooses",
        "list_items_click",
        "on_change_position",
        requireAll = false
    )
    fun setCategoriesList(
        view: RecyclerView, items: List<Category>?, category: Category?,
        listenerCategory: CategoryAdapterClickListener?,
        onChangePositionItemListener: OnChangePositionItemListener?,
    ) {
        items?.let {

            val adapter = AdapterCategoryList(
                listenerCategory, onChangePositionItemListener,
                it
            )
            view.adapter = adapter
            adapter.setActiveCategory(category)

            val callback: ItemTouchHelper.Callback = DragItemTouchHelper(adapter)
            val mItemTouchHelper = ItemTouchHelper(callback)
            mItemTouchHelper.attachToRecyclerView(view)
            adapter.setDragListener(object : AdapterCategoryList.OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
                    if (viewHolder != null) {
                        mItemTouchHelper.startDrag(viewHolder)
                    }
                }
            })

        }

    }
}