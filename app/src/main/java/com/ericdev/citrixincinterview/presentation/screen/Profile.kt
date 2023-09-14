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

@Destination
@Composable
fun Profile(
    viewModel: AppViewModel = hiltViewModel()
) {
    val localUserProfile by viewModel.localUserProfileState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getLocalUserById()
    }

    AppTheme {
        Surface {
            if (localUserProfile.isLoading) {
                Surface {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
            if (localUserProfile.error.isNotEmpty()) {
                Surface {
                    val error = "An error occurred while saving your data!\nClear and restart app"
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = error, textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            shape = RoundedCornerShape(4.dp),
                            onClick = {
                                // Unless in dev mode, I don't think this will happen under normal usage
                            }
                        ) {
                            Text(text = "YES, CLEAR")
                        }

                    }
                }
            }
            if (localUserProfile.localUser?.id?.isNotEmpty() == true) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize()
                ) {
                    val name =
                        (localUserProfile.localUser?.firstName + " " + localUserProfile.localUser?.lastName)

                    ProfileHeaderItem(
                        name = name,
                        status = if (localUserProfile.localUser?.active == true) "Active" else "Inactive",
                        email = localUserProfile.localUser?.email ?: "",
                        username = localUserProfile.localUser?.username ?: "",
                        createdOn = localUserProfile.localUser?.createdOn ?: ""
                    ) { }

                    val organizations = localUserProfile.localUser?.organization
                    val roles = localUserProfile.localUser?.roles

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
