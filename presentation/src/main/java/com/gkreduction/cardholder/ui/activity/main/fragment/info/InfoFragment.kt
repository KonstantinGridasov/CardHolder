package com.gkreduction.cardholder.ui.activity.main.fragment.info

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentInfoBinding
import com.gkreduction.cardholder.ui.base.BaseFragment

class InfoFragment : BaseFragment<InfoViewModel>(
    R.layout.fragment_info,
    InfoViewModel::class.java
) {

    override fun onStart() {
        super.onStart()
        (binding as FragmentInfoBinding).viewModel = viewModel
        (binding as FragmentInfoBinding).tvChangeTheme.setOnClickListener {
            setupDayNightMode()
        }
        initTheme()
    }


    private fun initTheme() {
        val currentMode = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
        (binding as FragmentInfoBinding).isDay = (currentMode != Configuration.UI_MODE_NIGHT_NO)
    }

    private fun setupDayNightMode() {
        val currentMode = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
        val mode = if (currentMode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        (binding as FragmentInfoBinding).isDay = (currentMode != Configuration.UI_MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(mode)
        viewModel?.saveTheme(mode)
    }

}