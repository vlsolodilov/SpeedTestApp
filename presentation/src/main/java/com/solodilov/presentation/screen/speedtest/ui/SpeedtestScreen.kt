package com.solodilov.presentation.screen.speedtest.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solodilov.presentation.component.LocationPermission
import com.solodilov.presentation.component.SpeedInfo
import com.solodilov.presentation.screen.speedtest.business.TestViewModel
import com.solodilov.util.R
import com.solodilov.util.annotation.TestStatus
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun SpeedTestScreen(
    viewModel: TestViewModel = koinViewModel(),
) {
    val state = viewModel.collectAsState().value

    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.name),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
        if (state.testStatus == TestStatus.NO_STARTED) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = viewModel::onStartClick,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.start),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                if (state.isConnected) {
                    Text(
                        text = stringResource(id = R.string.internet_connection),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.internet_no_connection),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    if (state.isVisibleDownload) {
                        SpeedInfo(
                            modifier = Modifier.weight(1f),
                            title = stringResource(id = R.string.download),
                            speed = state.downloadSpeed,
                        )
                    }
                    if (state.isVisibleUpload) {
                        SpeedInfo(
                            modifier = Modifier.weight(1f),
                            title = stringResource(id = R.string.upload),
                            speed = state.uploadSpeed,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = when (state.testStatus) {
                        TestStatus.STARTED -> stringResource(id = R.string.test_in_progress)
                        TestStatus.FINISHED -> stringResource(id = R.string.test_completed)
                        TestStatus.FAILED -> stringResource(id = R.string.test_failed)
                        else -> ""
                    },
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                if (state.testStatus == TestStatus.FINISHED) {
                    TextButton(onClick = viewModel::onStartClick) {
                        Text(
                            modifier = Modifier.padding(top = 24.dp),
                            text = stringResource(id = R.string.repeat).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        }
    }

    if (state.showLocationPermissionDialog) {
        LocationPermission(
            permissionGranted = viewModel::onLocationPermissionGranted,
            onDismiss = viewModel::hidePermissionDialog,
        )
    }
    BackHandler(onBack = {})
}