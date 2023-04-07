package com.gkreduction.cardholder.utils.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.cardholder.ui.activity.category.adapter.AdapterCategoryList
import com.gkreduction.cardholder.ui.activity.category.adapter.CategoryAdapterClickListener
import com.gkreduction.cardholder.ui.activity.edit_card.EditTextListener
import com.gkreduction.cardholder.ui.activity.main.adapter.AdapterCard
import com.gkreduction.cardholder.ui.activity.main.adapter.AdapterCategoryMain
import com.gkreduction.cardholder.ui.activity.main.adapter.CardClickListener
import com.gkreduction.cardholder.ui.activity.main.adapter.CategoryClickListener
import com.gkreduction.cardholder.ui.widjet.BarcodeView
import com.gkreduction.cardholder.ui.widjet.MyCardView
import com.gkreduction.cardholder.ui.widjet.carousel.CarouselLayoutManager
import com.gkreduction.cardholder.ui.widjet.carousel.CarouselRecyclerview
import com.gkreduction.cardholder.utils.AppTextWatcher
import com.gkreduction.domain.entity.Card
import com.gkreduction.domain.entity.Category
import com.gkreduction.domain.entity.ScanCode
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

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
    @BindingAdapter("set_color_gradient", "bottom_to_top", requireAll = false)
    fun setColorGradient(view: MyCardView, card: Card?, bottom_to_top: Boolean?) {
        card?.let {
            if (bottom_to_top == true)
                view.setBottomTopOrientation()
            view.changeColor(it.colorStart, it.colorEnd)
        }
    }


    @JvmStatic
    @BindingAdapter("edit_listeners", requireAll = false)
    fun setEditViewListener(view: EditText, listener: EditTextListener?) {
        val publishSubject: PublishSubject<String> = PublishSubject.create()
        listener?.onText(
            publishSubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        )

        view.addTextChangedListener(AppTextWatcher {
            it?.let { publishSubject.onNext(it.toString()) }
        })
    }


    @JvmStatic
    @BindingAdapter("items_cards", "item_listener", requireAll = false)
    fun setListCardIntoCarousel(
        view: CarouselRecyclerview, items: List<Card>?, listener: CardClickListener?
    ) {
        items?.let {
            val adapter = AdapterCard(listener)
            adapter.updateItems(it)
            view.adapter = adapter
            val manager =
                CarouselLayoutManager(
                    mInfinite = it.size > 2,
                    orientation = RecyclerView.VERTICAL,
                    intervalRatio = 0.8f
                )
            view.layoutManager = manager

        }
    }


    @JvmStatic
    @BindingAdapter(
        "list_categories_main", "item_categories_main",
        "category_choose", requireAll = false
    )
    fun setCategoriesToMain(
        view: RecyclerView, items: List<Category>?,
        category: Category?, listener: CategoryClickListener?
    ) {
        items?.let {
            val adapter = AdapterCategoryMain(listener)
            adapter.updateItems(it)
            adapter.setActiveCategory(category)
            view.adapter = adapter

            val layoutManager = FlexboxLayoutManager(view.context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                alignItems = AlignItems.STRETCH
            }
            view.layoutManager = layoutManager

        }
    }


    @JvmStatic
    @BindingAdapter("list_items", "item_chooses", "list_items_click", requireAll = false)
    fun setCategoriesList(
        view: RecyclerView, items: List<Category>?, category: Category?,
        listener: CategoryAdapterClickListener?
    ) {
        items?.let {
            val adapter = AdapterCategoryList(listener)
            adapter.updateItems(it)
            adapter.setActiveCategory(category)
            view.adapter = adapter
        }

    }
}