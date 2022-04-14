package com.raikerxv.model

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class RegionRepository(activity: AppCompatActivity) {

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    private val coarsePermissionChecker = PermissionChecker(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val geocoder = Geocoder(activity)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.request()
        return if (success) lastLocationSusupended() else null
    }

    @SuppressLint("MissingPermission")
    private suspend fun lastLocationSusupended(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }

    private fun Location?.toRegion(): String {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }

}