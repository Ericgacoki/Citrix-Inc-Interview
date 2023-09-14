package com.ericdev.citrixincinterview.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericdev.citrixincinterview.presentation.screen.destinations.ProfileDestination
import com.ericdev.citrixincinterview.presentation.screen.destinations.SignUpDestination
import com.ericdev.citrixincinterview.presentation.theme.AppTheme
import com.ericdev.citrixincinterview.presentation.viewmodel.AppViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator?,
    viewModel: AppViewModel = hiltViewModel()
) {
    val loggedInUserId by viewModel.loggedInUserId.collectAsState()

    AppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Citrix Inc.", style = MaterialTheme.typography.headlineLarge)
        }

        LaunchedEffect(key1 = Unit, block = {
            delay(3000L)
            if (loggedInUserId != null) {
                navigator?.popBackStack()
                navigator?.navigate(ProfileDestination)
            } else {
                navigator?.popBackStack()
                navigator?.navigate(SignUpDestination)
            }
        })
    }
}
