package com.baykal.moviedb.base

import android.app.AlertDialog
import android.content.Context
import android.widget.ProgressBar

object DialogUtil {
    var dialog: AlertDialog? = null

    fun showLoading(context: Context?) {
        closeDialog()
        val progressBar = ProgressBar(context)
        dialog = AlertDialog.Builder(context)
            .setView(progressBar)
            .create()
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