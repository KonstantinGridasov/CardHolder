package com.gkreduction.cardholder.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.base.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<SplashScreenViewModel>(
    R.layout.activity_splash,
    SplashScreenViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListeners()
        viewModel.getTheme()
    }

    private fun initListeners() {
        viewModel.isThemeSet.observe(this) { isSet ->
            if (isSet)
                navigateToHome()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
}