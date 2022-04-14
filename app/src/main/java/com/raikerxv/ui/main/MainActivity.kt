package com.raikerxv.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.raikerxv.databinding.ActivityMainBinding
import com.raikerxv.model.Movie
import com.raikerxv.model.MoviesRepository
import com.raikerxv.ui.detail.DetailActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(MoviesRepository(this)) }
    private val adapter = MoviesAdapter { viewModel.onMovieClick(it) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(::updateUI)
            }
        }
    }

    private fun updateUI(state: MainViewModel.MainUIState) {
        binding.progress.isVisible = state.loading
        state.movies?.let(adapter::submitList)
        state.navigateTo?.let(::navigateTo)
    }

    private fun navigateTo(movie: Movie) {
        Intent(this, DetailActivity::class.java)
            .apply { putExtra(DetailActivity.MOVIE, movie) }
            .let { startActivity(it) }
    }
}