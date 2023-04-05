package com.gkreduction.cardholder.ui.activity.edit_card

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.gkreduction.cardholder.ui.widjet.BarcodeView
import com.gkreduction.cardholder.utils.AppTextWatcher
import com.gkreduction.domain.entity.ScanCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

object BindingCard {
    @JvmStatic
    @BindingAdapter(
        "set_barcode",
        requireAll = false
    )
    fun setSetBarcode(
        view: BarcodeView,
        barcode: ScanCode?,
    ) {
        barcode?.let {
            view.scanCode = it
        }
    }


    @JvmStatic
    @BindingAdapter(
        "edit_listeners",
        requireAll = false
    )
    fun setEditListener(
        view: EditText,
        listener: EditTextListener?,
    ) {

        val publishSubject: PublishSubject<String> = PublishSubject.create()
        listener?.onText(
            publishSubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        )

        view.addTextChangedListener(AppTextWatcher {
            it?.let {
                publishSubject.onNext(it.toString())
            }
        })

    }

}


