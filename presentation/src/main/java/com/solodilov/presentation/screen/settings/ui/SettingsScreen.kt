package com.solodilov.presentation.screen.settings.ui

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solodilov.presentation.component.AnnotatedFieldSelector
import com.solodilov.presentation.screen.settings.business.SettingsViewModel
import com.solodilov.util.BasicSideEffect
import com.solodilov.util.NavigateTo
import com.solodilov.util.R
import com.solodilov.util.ShowMessage
import com.solodilov.util.annotation.ThemeMode
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        handleSideEffect(
            sideEffect = sideEffect,
            context = context,
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
            AnnotatedFieldSelector(
                label = stringResource(id = R.string.theme),
                selectedOption = state.selectedTheme,
                optionMap = mapOf(
                    ThemeMode.SYSTEM to stringResource(id = R.string.system),
                    ThemeMode.LIGHT to stringResource(id = R.string.light),
                    ThemeMode.DARK to stringResource(id = R.string.dark),
                ),
                onOptionSelect = viewModel::onThemeChange,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.check_speed),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = state.isCheckedDownload,
                    onCheckedChange = viewModel::onDownloadClick,
                )
                Text(
                    text = stringResource(R.string.download),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = state.isCheckedUpload,
                    onCheckedChange = viewModel::onUploadClick,
                )
                Text(
                    text = stringResource(R.string.upload),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
        Row(
            modifier = Modifier
                .height(58.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = viewModel::onCancelClick) {
                Text(
                    text = stringResource(id = R.string.cancel).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                )
            }
            TextButton(onClick = viewModel::onSaveSettingsClick) {
                Text(
                    text = stringResource(id = R.string.save).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
    BackHandler(onBack = {})
}

private fun handleSideEffect(
    sideEffect: BasicSideEffect,
    context: Context,
) {
    when (sideEffect) {
        is NavigateTo -> {}
        is ShowMessage -> Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
    }
}