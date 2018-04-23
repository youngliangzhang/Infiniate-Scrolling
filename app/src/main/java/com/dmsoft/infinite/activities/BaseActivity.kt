package com.dmsoft.infinite.activities

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.TextView
import com.dmsoft.infinite.R

abstract class BaseActivity: AppCompatActivity() {
    abstract fun getRootView(): ViewGroup?

    fun showSuccessMessage(message: String) {
        showSnackBar(message, true)
    }

    fun showErrorMessage(message: String) {
        showSnackBar(message, false)
    }

    private fun showSnackBar(message: String, isSuccess: Boolean) {
        getRootView()?.let {
            try {
                Snackbar.make(it, message, Snackbar.LENGTH_LONG).apply {
                    setActionTextColor(Color.LTGRAY)
                    view.run {
                        setBackgroundColor(ResourcesCompat.getColor(resources, if (isSuccess) R.color.success_notification_bar else R.color.error_notification_bar, theme))
                        findViewById<TextView>(android.support.design.R.id.snackbar_text)
                                .setTextColor(ResourcesCompat.getColor(resources,
                                        if (isSuccess) R.color.success_notification_bar_text else R.color.error_notification_bar_text, theme))
                    }
                }.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}