package com.raikerxv.model

import android.app.Application
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class PermissionChecker(private val application: Application, private val permission: String) {

    fun check(): Boolean =
        ContextCompat.checkSelfPermission(application, permission) == PackageManager.PERMISSION_GRANTED

}