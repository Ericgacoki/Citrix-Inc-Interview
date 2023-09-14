package com.ericdev.citrixincinterview.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericdev.citrixincinterview.presentation.component.OrganizationItem
import com.ericdev.citrixincinterview.presentation.component.ProfileHeaderItem
import com.ericdev.citrixincinterview.presentation.component.RoleItem
import com.ericdev.citrixincinterview.presentation.theme.AppTheme
import com.ericdev.citrixincinterview.presentation.viewmodel.AppViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ClickedUserDetails(
    viewModel: AppViewModel = hiltViewModel(),
    navigator: DestinationsNavigator?,
    clickedUserId: String,
) {
    val clickedUserDetailsState by viewModel.clickedUserDetailsState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.getClickedUserDetails(id = clickedUserId)
    }

    AppTheme {
        Surface {
            if (clickedUserDetailsState.isLoading) {
                Surface {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
            if (clickedUserDetailsState.error.isNotEmpty()) {
                Surface {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = clickedUserDetailsState.error, textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            shape = RoundedCornerShape(4.dp),
                            onClick = { viewModel.getClickedUserDetails(id = clickedUserId) }
                        ) {
                            Text(text = "RETRY")
                        }
                    }
                }
            }
            if (clickedUserDetailsState.userDetails?.id?.isNotEmpty() == true) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize()
                ) {
                    val name =
                        (clickedUserDetailsState.userDetails?.firstName + " " + clickedUserDetailsState.userDetails?.lastName)

                    ProfileHeaderItem(
                        showBackArrow = true,
                        name = name,
                        status = if (clickedUserDetailsState.userDetails?.active == true) "Active" else "Inactive",
                        email = clickedUserDetailsState.userDetails?.email ?: "",
                        username = clickedUserDetailsState.userDetails?.username ?: "",
                        createdOn = clickedUserDetailsState.userDetails?.createdOn ?: "",
                        onClickBackArrow = {
                           navigator?.navigateUp()
                        }
                    )

                    val organizations = clickedUserDetailsState.userDetails?.organization
                    val roles = clickedUserDetailsState.userDetails?.roles

                    if (organizations != null) {
                        Text(
                            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                            text = "Organization",
                            style = MaterialTheme.typography.titleSmall,
                            fontSize = 16.sp,
                        )
                        OrganizationItem(organizationName = organizations.organizationName)
                    }

                    if (roles != null) {
                        Text(
                            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                            text = "Role",
                            style = MaterialTheme.typography.titleSmall,
                            fontSize = 16.sp,
                        )

                        roles.forEach { role ->
                            RoleItem(
                                roleName = role.name,
                                roleDescription = role.description,
                                createdOn = role.createdOn,
                                roleStatus = if (role.active) "Active" else "Inactive"
                            )
                        }
                    }
                }
            }
        }
    }
}
