/*
 * Copyright (c) 2024. Ryan Wong
 * https://github.com/ryanw-mobile
 * Sponsored by RW MobiMedia UK Limited
 *
 */

package com.rwmobi.dazncodechallenge.ui.destinations.schedule

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import coil.ImageLoader
import com.rwmobi.dazncodechallenge.R
import com.rwmobi.dazncodechallenge.ui.components.NoDataScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    uiState: ScheduleUIState,
    uiEvent: ScheduleUIEvent,
) {
    if (uiState.errorMessages.isNotEmpty()) {
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val errorMessageText = errorMessage.message

        LaunchedEffect(errorMessage.id) {
            uiEvent.onShowSnackbar(errorMessageText)
            uiEvent.onErrorShown(errorMessage.id)
        }
    }

    val context = LocalContext.current
    PullToRefreshBox(
        modifier = modifier.semantics { contentDescription = context.getString(R.string.content_description_pull_to_refresh) },
        isRefreshing = uiState.isLoading,
        onRefresh = {
            uiEvent.onRefresh()
        },
    ) {
        uiState.schedules?.let { schedules ->
            if (schedules.isNotEmpty()) {
                SchedulesList(
                    modifier = Modifier.fillMaxSize(),
                    schedules = schedules,
                    imageLoader = imageLoader,
                    requestScrollToTop = uiState.requestScrollToTop,
                    onScrolledToTop = uiEvent.onScrolledToTop,
                )
            } else if (!uiState.isLoading) {
                NoDataScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()), // to support pull to refresh
                )
            }
        }
    }

    LaunchedEffect(true) {
        uiEvent.onInitialLoad()

        while (isActive) {
            delay(30_000)
            uiEvent.onRefresh()
        }
    }
}
