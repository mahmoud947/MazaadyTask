package com.example.core.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.core.dialogs.ErrorDialog
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : androidx.fragment.app.Fragment() {
    abstract val viewModel: BaseViewModel
    lateinit var errorDialog: ErrorDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        errorDialog =ErrorDialog(activity = requireActivity(), buttonTitle = "Retry")
    }

    override fun onStart() {
        super.onStart()

        errorDialog.onClick {
            errorDialog.dismissDialog()
        }


        viewModel.showErrorMessage.observe(this) {
            if (it != null) {
                errorDialog.updateMessage(it.message)
                errorDialog.updateButtonTitle(it.button)
                errorDialog.startDialog()
            }
        }


        viewModel.showToast.observe(this) {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }
        viewModel.showSnackBar.observe(this) {
            Snackbar.make(this.requireView(), it, Snackbar.LENGTH_LONG).show()
        }
        viewModel.showLoading.observe(this) {

        }


        viewModel.showSnackBarInt.observe(this) {
            Snackbar.make(this.requireView(), getString(it), Snackbar.LENGTH_LONG).show()
        }

    }


    fun Context.hasPermissions( permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}
