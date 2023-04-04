package com.gkreduction.cardholder.ui.activity.main

import android.content.Intent
import android.os.Bundle
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.constant.CARD_ID
import com.gkreduction.cardholder.databinding.ActivityMainBinding
import com.gkreduction.cardholder.ui.activity.edit_card.EditCardActivity
import com.gkreduction.cardholder.ui.activity.card.CardActivity
import com.gkreduction.cardholder.ui.activity.main.adapter.CardClickListener
import com.gkreduction.cardholder.ui.base.BaseActivity
import com.gkreduction.cardholder.ui.activity.main.adapter.CategoryClickListener
import com.gkreduction.domain.entity.Category


class MainActivity :
    BaseActivity<MainViewModel>(R.layout.activity_main, MainViewModel::class.java),
    CardClickListener, CategoryClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (binding as ActivityMainBinding).viewModel = viewModel
        initListeners()
    }


    override fun onResume() {
        super.onResume()
        viewModel.getAllCategories()
    }


    private fun initListeners() {
        (binding as ActivityMainBinding).toCamera.setOnClickListener {
            navigateToCameraActivity()
        }
        (binding as ActivityMainBinding).showAllCategory.setOnClickListener {
            viewModel.changeShowCategories()
        }
        (binding as ActivityMainBinding).itemListener = this
        (binding as ActivityMainBinding).categoryChoose = this

    }


    private fun navigateToCameraActivity() {
        startActivity(Intent(this, EditCardActivity::class.java))
    }

    override fun onItemClick(id: Long) {
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra(CARD_ID, id)
        startActivity(intent)
    }

    override fun onItemClick(category: Category?) {
      viewModel.sortListByCategory(category)
    }
}

