package com.raikerxv.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.raikerxv.databinding.ActivityMainBinding
import com.raikerxv.model.MoviesRepository
import com.raikerxv.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val moviesRepository by lazy { MoviesRepository(this) }

    private val adapter = MoviesAdapter {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, it)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ActivityMainBinding.inflate(layoutInflater)) {
            setContentView(root)

            recycler.adapter = adapter

            lifecycleScope.launch {
                progress.visibility = View.VISIBLE
                adapter.submitList(moviesRepository.findPopularMovies().results)
                progress.visibility = View.GONE
            }

        }
    }
}