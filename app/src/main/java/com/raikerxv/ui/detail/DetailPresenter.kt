package com.raikerxv.ui.detail

import com.raikerxv.model.Movie

class DetailPresenter {

    interface DetailView {
        fun updateUI(movie: Movie)
    }

    private var detailView: DetailView? = null

    fun onCreate(detailView: DetailView, movie: Movie) {
        this.detailView = detailView
        detailView.updateUI(movie)
    }

    fun onDestroy() {
        this.detailView = null
    }

}