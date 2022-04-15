package com.raikerxv.ui.detail

import androidx.databinding.BindingAdapter
import com.raikerxv.domain.Movie

@BindingAdapter("movie")
fun MovieDetailInfoView.bindMovie(movie: Movie?) {
    movie?.let { setMovie(it) }
}