package com.raikerxv.model

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class PermissionChecker(activity: AppCompatActivity, private val permission: String) {

    private var onRequest: (Boolean) -> Unit = {}
    private val launcher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        onRequest(isGranted)
    }

    suspend fun request(): Boolean =
        suspendCancellableCoroutine { continuation ->
            onRequest = {
                continuation.resume(it)
            }
            launcher.launch(permission)
        }

}