package com.gkreduction.cardholder.ui.activity.card

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.CARD_ID
import com.gkreduction.cardholder.databinding.ActivityCardBinding
import com.gkreduction.cardholder.ui.activity.edit_card.EditCardActivity
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.cardholder.ui.dialog.DialogInfo

class CardActivity :
    BaseActivity<CardViewModel>(R.layout.activity_card, CardViewModel::class.java) {
    private var isRevert = false
    var id = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        setSupportActionBar((binding as ActivityCardBinding).toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_card, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> navigateToEdit()
            R.id.action_delete -> showDialogDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogDelete() {
        val info = viewModel.card.get()?.cardName ?: ""
        val dialog = DialogInfo()
        dialog.setListener(info) {
            if (it) {
                viewModel.deleteCard()
                finish()
            }
        }
        dialog.show(supportFragmentManager, "")
    }

    private fun navigateToEdit() {
        val intent = Intent(this, EditCardActivity::class.java)
        intent.putExtra(CARD_ID, id)
        startActivity(intent)
    }

    private fun animation(view: View) {
        val rotate = (binding as ActivityCardBinding).cardRotate
        val animatorRotate = ObjectAnimator.ofFloat(rotate, View.ROTATION_Y, 0f, 180f)
            .setDuration(500)

        animatorRotate.apply {
            doOnStart {
                view.visibility = View.INVISIBLE
                rotate.visibility = View.VISIBLE
            }
            doOnEnd {
                rotate.visibility = View.GONE
                view.visibility = View.VISIBLE
            }
        }
        animatorRotate.start()
    }
}