package com.baykal.moviedb.base

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.updateLayoutParams

object DialogUtil {
    var dialog: AlertDialog? = null

    fun showLoading(context: Context?) {
        closeDialog()
        val progressBar = ProgressBar(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            indeterminateTintList = ColorStateList.valueOf(Color.BLUE)
        }
        dialog = AlertDialog.Builder(context)
            .setView(progressBar)
            .create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    fun showError(context: Context?, message: String?) {
        closeDialog()
        dialog = AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Okay") { d, _ ->
                d.dismiss()
            }
            .create()
        dialog?.show()
    }

    fun closeDialog() {
        dialog?.dismiss()
        dialog = null
    }
}