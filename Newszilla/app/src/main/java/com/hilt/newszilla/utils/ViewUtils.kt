package com.hilt.newszilla.utils

import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun FragmentActivity.showAlertDialog() {

    AlertDialog.Builder(this)
        .setTitle("Error")
        .setMessage("Internet not available, Cross check your internet connectivity and try again later...")
        .setCancelable(false)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setPositiveButton(
            " Enable Internet "
        ) {

                dialog, id ->
            run {
                val dialogIntent = Intent(Settings.ACTION_SETTINGS);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(dialogIntent)
                this.finishAffinity()

            }

        }
        .setNeutralButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                run {
                    dialog.cancel()
                    this.finishAffinity()

                }

            }).show()

}

fun Context.transitionAnimationBundle(): Bundle {

    return ActivityOptions.makeCustomAnimation(
        this,
        android.R.anim.fade_in,
        android.R.anim.fade_out
    ).toBundle()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}


fun Activity.setFullScreen() {


    this.window.decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    )


}


fun Activity.setFullScreenForNotch() {
    this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        this.window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
    }

}


fun Activity.adjustToolBarMarginForNotchDevices(toolbar: Toolbar) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val windowInsets = this.window.decorView.rootWindowInsets
        if (windowInsets != null) {
            val displayCutout = windowInsets.displayCutout
            if (displayCutout != null) {
                val safeInsetTop = displayCutout.safeInsetTop
                val newLayoutParams = toolbar.layoutParams as ViewGroup.MarginLayoutParams
                newLayoutParams.setMargins(0, safeInsetTop, 0, 0)
                toolbar.layoutParams = newLayoutParams

            }
        }
    }

}




