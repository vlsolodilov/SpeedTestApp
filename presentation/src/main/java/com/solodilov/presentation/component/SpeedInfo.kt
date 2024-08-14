package com.solodilov.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.solodilov.util.R
import com.solodilov.util.annotation.TestStatus

@Composable
fun SpeedInfo(
    modifier: Modifier = Modifier,
    title: String,
    speed: Double,
    @TestStatus testStatus: Int,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = " Mbps",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
            )
        }
        Text(
            text = speed.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Text(
            text = when (testStatus) {
                TestStatus.STARTED -> stringResource(id = R.string.test_in_progress)
                TestStatus.FINISHED -> stringResource(id = R.string.test_completed)
                TestStatus.FAILED -> stringResource(id = R.string.test_failed)
                else -> ""
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}