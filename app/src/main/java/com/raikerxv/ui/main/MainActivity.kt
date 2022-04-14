package com.raikerxv.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.raikerxv.databinding.ActivityMainBinding
import com.raikerxv.model.Movie
import com.raikerxv.model.MoviesRepository
import com.raikerxv.ui.detail.DetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.MainView {

    private val presenter by lazy { MainPresenter(MoviesRepository(this), lifecycleScope) }
    private val adapter = MoviesAdapter { presenter.onMovieClick(it) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter
        presenter.onCreate(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun updateData(movies: List<Movie>) {
        adapter.submitList(movies)
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun navigateTo(movie: Movie) {
        Intent(this, DetailActivity::class.java)
            .apply { putExtra(DetailActivity.MOVIE, movie) }
            .let { startActivity(it) }
    }
}