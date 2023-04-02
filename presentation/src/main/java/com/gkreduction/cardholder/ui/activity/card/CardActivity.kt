package com.gkreduction.cardholder.ui.activity.card

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.CARD_ID
import com.gkreduction.cardholder.databinding.ActivityCardBinding
import com.gkreduction.cardholder.ui.base.BaseActivity

class CardActivity :
    BaseActivity<CardViewModel>(R.layout.activity_card, CardViewModel::class.java) {
    private var isRevert = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var id = 0L
        if (intent.extras != null) {
            id = intent.extras!!.getLong(CARD_ID)

        }

        (binding as ActivityCardBinding).viewmodel = viewModel
        (binding as ActivityCardBinding).isRevert = isRevert
        viewModel.getCards(id)

        (binding as ActivityCardBinding).rotate.setOnClickListener {
            animation((binding as ActivityCardBinding).cardItem)
            (binding as ActivityCardBinding).isRevert = !isRevert
            isRevert = !isRevert
        }


    }

    private fun animation(view: View) {
        val animatorRotate = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 0f, 180f, 360f)
            .setDuration(1000)
        val animatorAlpha = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f, 1f)
            .setDuration(1000)

        val animationSet = AnimatorSet()
        animationSet.playTogether(animatorRotate)
        animationSet.playTogether(animatorAlpha)
        animationSet.start()




    }
}