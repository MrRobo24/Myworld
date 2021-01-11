package com.example.myworld.adapter.authadapter


import androidx.databinding.BindingAdapter

import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("tilError")
fun tilError(view: TextInputLayout, errorString: String?) {
    view.isErrorEnabled = true
    view.error = errorString
}