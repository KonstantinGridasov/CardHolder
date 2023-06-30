package com.gkreduction.cardholder.ui.activity.main.fragment.add

import io.reactivex.Observable

interface EditTextListener {
    fun onText(s: Observable<String>)
}