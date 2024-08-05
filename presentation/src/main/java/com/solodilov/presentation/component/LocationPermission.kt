package com.solodilov.presentation.component

import android.Manifest
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermission(
    permissionGranted: () -> Unit,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    var permissionAlreadyRequested by rememberSaveable { mutableStateOf(false) }
    val permission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION) {
        permissionAlreadyRequested = true
    }

    @Composable
    fun RequestPermission() {
        SideEffect { permission.launchPermissionRequest() }
    }

    when {
        permission.status.isGranted -> {
            onDismiss()
            permissionGranted()
        }
        permission.status is PermissionStatus.Denied && permissionAlreadyRequested -> {
            Toast.makeText(
                context,
                "Error location",
                Toast.LENGTH_SHORT
            ).show()
            onDismiss()
        }
        else -> RequestPermission()
    }
}