package com.raikerxv.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.raikerxv.R
import com.raikerxv.databinding.ActivityMainBinding
import com.raikerxv.model.MoviesRepository
import com.raikerxv.model.RemoteConnection
import kotlin.coroutines.resume
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

class MainActivity : AppCompatActivity() {

    private val moviesRepository by lazy { MoviesRepository(this) }

    private val adapter = MoviesAdapter{
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, it)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            adapter.submitList(moviesRepository.findPopularMovies().results)
        }
    }
}