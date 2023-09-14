package com.ericdev.citrixincinterview.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericdev.citrixincinterview.presentation.component.AppSearchBar
import com.ericdev.citrixincinterview.presentation.component.UserListItem
import com.ericdev.citrixincinterview.presentation.screen.destinations.ClickedUserDetailsDestination
import com.ericdev.citrixincinterview.presentation.theme.AppTheme
import com.ericdev.citrixincinterview.presentation.viewmodel.AppViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun AllUsers(
    viewModel: AppViewModel = hiltViewModel(),
    navigator: DestinationsNavigator?
) {
    val usersState by viewModel.allUsersState.collectAsState()

    AppTheme {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (usersState.isLoading) {
                Surface {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
            if (usersState.error.isNotEmpty()) {
                Surface {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = usersState.error, textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            shape = RoundedCornerShape(4.dp),
                            onClick = {
                                viewModel.retryFetchingData()
                            }
                        ) {
                            Text(text = "RETRY")
                        }
                    }
                }
            }
            if (usersState.users.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    stickyHeader {
                        AppSearchBar(
                            title = "Users",
                            showSearchBar = true,
                            initialValue = "",
                            onSearchParamChange = { newParams ->

                            }
                        )
                    }

                    itemsIndexed(usersState.users) { index, user ->
                        val name = user.firstName + " " + user.lastName
                        UserListItem(
                            name = name,
                            username = user.username,
                            isActive = user.active,
                            onClick = {
                                navigator?.navigate(ClickedUserDetailsDestination(user.id))
                            }
                        )
                        if (index != usersState.users.lastIndex) {
                            Divider()
                        }
                    }
                    item {
                        Spacer(
                            modifier = Modifier.height(100.dp)
                        )
                    }
                }
            }
        }
    }
}
