package com.gkreduction.cardholder.ui.activity.card

import android.os.Bundle
import android.util.Log
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.CARD_ID
import com.gkreduction.cardholder.constant.TYPE_SCAN
import com.gkreduction.cardholder.databinding.ActivityCardBinding
import com.gkreduction.cardholder.ui.activity.base.BaseActivity
import com.gkreduction.cardholder.ui.activity.camera.CameraActivity

class CardActivity :
    BaseActivity<CardViewModel>(R.layout.activity_card, CardViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var id = 0L
        if (intent.extras != null) {
            id = intent.extras!!.getLong(CARD_ID)

        }

        Log.d("CARDHOLDER_APP", "onCreate = ${id}")

        (binding as ActivityCardBinding).viewmodel = viewModel
        viewModel.getCards(id)
    }
}