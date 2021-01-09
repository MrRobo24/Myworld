package com.example.myworld.utilites

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myworld.fragment.authfragments.LoginFragment
import com.example.myworld.fragment.authfragments.SignUpFragment
import com.example.myworld.webservices.Resource
import com.google.android.material.snackbar.Snackbar

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackBar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 400 -> {
            if (this is SignUpFragment) {
                requireView().snackBar("Failed to SignUp")
            }
        }
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackBar("You've entered incorrect email or password")
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackBar(error)
        }
    }
}