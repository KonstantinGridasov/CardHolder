package com.gkreduction.cardholder.ui.activity.edit_card

import io.reactivex.Observable

interface EditTextListener {
    fun onText(s: Observable<String>)
}