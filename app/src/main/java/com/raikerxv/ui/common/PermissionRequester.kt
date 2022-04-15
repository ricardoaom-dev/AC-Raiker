package com.raikerxv.ui.common

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class PermissionRequester(fragment: Fragment, private val permission: String) {

    private var onRequest: (Boolean) -> Unit = {}
    private val launcher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            onRequest(isGranted)
        }

    suspend fun request(): Boolean =
        suspendCancellableCoroutine { continuation ->
            onRequest = { continuation.resume(it) }
            launcher.launch(permission)
        }

}