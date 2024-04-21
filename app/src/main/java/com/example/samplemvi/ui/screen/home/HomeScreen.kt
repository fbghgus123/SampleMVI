package com.example.samplemvi.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.samplemvi.ui.screen.home.HomeContract.HomeEvent
import com.example.samplemvi.ui.screen.home.HomeContract.HomeSideEffect
import com.example.samplemvi.ui.screen.home.HomeContract.HomeState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeRoute(
    navigateToSecondPage: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by viewModel.viewState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(false)

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is HomeSideEffect.NavigateToSecondPage -> navigateToSecondPage()
            }
        }
    }

    if (isLoading) Text("Loading") // 로딩 스크린 or ProgressBar
    else HomeScreen(
        state = homeState,
        modifier = modifier,
        onClickSecondPageButton = { viewModel.sendEvent(HomeEvent.OnClickNavigateButton) },
        onClickPlusNumButton = { viewModel.sendEvent(HomeEvent.OnClickPlusButton) }
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    modifier: Modifier = Modifier,
    onClickSecondPageButton: () -> Unit,
    onClickPlusNumButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text("Current Number: ${state.num}")
        Button(
            onClick = onClickPlusNumButton
        ) {
            Text("Plus Number")
        }
        Button(
            onClick = onClickSecondPageButton
        ) {
            Text("Second Page")
        }
    }
}

