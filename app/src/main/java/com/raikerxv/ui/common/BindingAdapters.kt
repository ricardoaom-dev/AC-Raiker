package com.raikerxv.ui.common

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.raikerxv.ui.loadUrl

@BindingAdapter("isVisible")
fun View.setVisible(visible: Boolean?) {
    visible?.let { isVisible = it }
}

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    url?.let { loadUrl(it) }
}