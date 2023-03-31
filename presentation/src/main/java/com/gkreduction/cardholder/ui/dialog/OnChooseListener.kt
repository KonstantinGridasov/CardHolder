package com.gkreduction.cardholder.ui.dialog

import com.gkreduction.domain.entity.Category

interface OnChooseListener {
    fun onChoose(category: Category?)
}