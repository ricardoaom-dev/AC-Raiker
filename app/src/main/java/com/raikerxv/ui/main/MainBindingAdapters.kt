package com.raikerxv.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raikerxv.model.Movie

@BindingAdapter("items")
fun RecyclerView.settItems(movies: List<Movie>?) {
    movies?.let { (adapter as? MoviesAdapter)?.submitList(it) }
}