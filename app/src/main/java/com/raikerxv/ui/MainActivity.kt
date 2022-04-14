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
import com.raikerxv.model.RemoteConnection
import kotlin.coroutines.resume
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            lifecycleScope.launch {
                val location = getLocation(isGranted)
                val movies = RemoteConnection.service.listPopularMovies(
                    getString(R.string.api_key),
                    getRegionFromLocation(location)
                )
                adapter.movies = movies.results
            }
        }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private suspend fun getLocation(granted: Boolean): Location? {
        return if (granted) findLastLocation() else null
    }

    @SuppressLint("MissingPermission")
    private suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }

    private fun getRegionFromLocation(location: Location?): String {
        val geocoder = Geocoder(this@MainActivity)
        val fromLocation = location?.let {
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
        }
        return fromLocation?.firstOrNull()?.countryCode ?: "US"
    }
}