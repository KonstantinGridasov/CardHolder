package com.gkreduction.cardholder.ui.fragment.add

import io.reactivex.Observable

interface EditTextListener {
    fun onText(s: Observable<String>)
}