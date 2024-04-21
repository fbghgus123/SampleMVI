package com.example.samplemvi.ui.screen.second

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.samplemvi.ui.screen.second.SecondContract.SecondEvent
import com.example.samplemvi.ui.screen.second.SecondContract.SecondState

@Composable
fun SecondRoute(
    modifier: Modifier = Modifier,
    viewModel: SecondViewModel = hiltViewModel()
) {
    val secondState by viewModel.viewState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(false)

    if (isLoading) Text("Loading") // 로딩 스크린 or ProgressBar
    else SecondScreen(
        state = secondState,
        onClickMinusButton = { viewModel.sendEvent(SecondEvent.OnClickMinusButton) }
    )
}

@Composable
private fun SecondScreen(
    state: SecondState,
    modifier: Modifier = Modifier,
    onClickMinusButton: () -> Unit
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
            onClick = onClickMinusButton
        ) {
            Text("Minus Number")
        }
    }
}