package com.dmsoft.infinite.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup

import com.dmsoft.infinite.R

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }

    override fun getRootView(): ViewGroup? {
        return null
    }
}