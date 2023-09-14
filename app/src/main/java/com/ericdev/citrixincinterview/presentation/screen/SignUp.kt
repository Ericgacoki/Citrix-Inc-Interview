package com.ericdev.citrixincinterview.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericdev.citrixincinterview.R
import com.ericdev.citrixincinterview.presentation.screen.destinations.ProfileDestination
import com.ericdev.citrixincinterview.presentation.theme.AppTheme
import com.ericdev.citrixincinterview.presentation.viewmodel.AppViewModel
import com.ericdev.validitychecker.ValidityChecker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SignUp(
    navigator: DestinationsNavigator?,
    viewModel: AppViewModel = hiltViewModel()
) {
    val organizationState by viewModel.organizationState.collectAsState()
    val organizationsPair by viewModel.organizationsPair.collectAsState()
    val selectedOrganizationPair by viewModel.selectedOrganizationPair.collectAsState()

    val rolesState by viewModel.rolesState.collectAsState()
    val rolesPair by viewModel.rolesPair.collectAsState()
    val selectedRolePair by viewModel.selectedRolePair.collectAsState()

    val allUsersState by viewModel.allUsersState.collectAsState()

    val authState = viewModel.authState.value

    var isOrganizationMenuExpanded by remember { mutableStateOf(false) }
    var isRoleMenuExpanded by remember { mutableStateOf(false) }

    val firstNameFocusManager = LocalFocusManager.current
    val firstNameFocusRequester = remember { FocusRequester() }
    var isFirstNameError by remember { mutableStateOf(false) }
    var inputFirstName by remember { mutableStateOf(viewModel.inputFirstName) }

    val lastNameFocusManager = LocalFocusManager.current
    val lastNameFocusRequester = remember { FocusRequester() }
    var isLastNameError by remember { mutableStateOf(false) }
    var inputLastName by remember { mutableStateOf(viewModel.inputFirstName) }

    val userNameFocusManager = LocalFocusManager.current
    val userNameFocusRequester = remember { FocusRequester() }
    var isUserNameError by remember { mutableStateOf(false) }
    var inputUserName by remember { mutableStateOf(viewModel.inputFirstName) }

    val emailFocusManager = LocalFocusManager.current
    val emailFocusRequester = remember { FocusRequester() }
    var isEmailError by remember { mutableStateOf(false) }
    var inputEmail by remember { mutableStateOf(viewModel.inputFirstName) }

    val passwordFocusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var inputPassword by remember { mutableStateOf(viewModel.inputPassword) }
    var isPasswordValidMsg by remember { mutableStateOf("") }

    val confirmPasswordFocusManager = LocalFocusManager.current
    val confirmPasswordFocusRequester = remember { FocusRequester() }
    var isConfirmPasswordError by remember { mutableStateOf(false) }
    var inputConfirmPassword by remember { mutableStateOf(viewModel.inputConfirmPassword) }

    AppTheme {
        val context = LocalContext.current

        if (
            organizationState.isLoading or
            rolesState.isLoading or
            allUsersState.isLoading
        ) {
            Surface {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        } else if (
            organizationState.error.isNotEmpty() or
            rolesState.error.isNotEmpty() or
            allUsersState.error.isNotEmpty()
        ) {
            Surface {
                val error =
                    organizationState.error.ifEmpty { rolesState.error.ifEmpty { allUsersState.error } }
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
                            viewModel.retryFetchingData()
                        }
                    ) {
                        Text(text = "RETRY")
                    }

                }
            }
        } else {
            // At this point, we have the organizations and roles to be used in the drop down
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize()
                ) {
                    Text(text = "Sign Up", style = MaterialTheme.typography.titleLarge)
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "Fill the fields below to create a Citrix account",
                        style = MaterialTheme.typography.bodySmall
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                    ) {
                        item {

                            //First Name Text Field
                            OutlinedTextField(
                                modifier = Modifier
                                    .scale(scaleY = 0.9F, scaleX = 1F)
                                    .focusRequester(firstNameFocusRequester),
                                shape = RoundedCornerShape(4.dp),
                                value = inputFirstName,
                                singleLine = true,
                                isError = isFirstNameError,
                                onValueChange = { newValue ->
                                    inputFirstName = newValue.trim()
                                    viewModel.inputFirstName = newValue.trim()
                                },
                                label = {
                                    Text(text = "First Name")
                                },
                                keyboardActions = KeyboardActions(onNext = {
                                    firstNameFocusManager.clearFocus(force = true)
                                    lastNameFocusRequester.requestFocus()
                                }),
                                keyboardOptions = KeyboardOptions(
                                    autoCorrect = false,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                )
                            )
                        }

                        item {

                            //Last Name Text Field
                            OutlinedTextField(
                                modifier = Modifier
                                    .scale(scaleY = 0.9F, scaleX = 1F)
                                    .focusRequester(lastNameFocusRequester),
                                shape = RoundedCornerShape(4.dp),
                                value = inputLastName,
                                singleLine = true,
                                isError = isLastNameError,
                                onValueChange = { newValue ->
                                    inputLastName = newValue.trim()
                                    viewModel.inputLastName = newValue.trim()
                                },
                                label = {
                                    Text(text = "Last Name")
                                },
                                keyboardActions = KeyboardActions(onNext = {
                                    lastNameFocusManager.clearFocus(force = true)
                                    userNameFocusRequester.requestFocus()
                                }),
                                keyboardOptions = KeyboardOptions(
                                    autoCorrect = false,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                )
                            )
                        }
                    }

                    //Username Text Field
                    OutlinedTextField(
                        modifier = Modifier
                            .scale(scaleY = 0.9F, scaleX = 1F)
                            .fillMaxWidth(fraction = 1F)
                            .focusRequester(userNameFocusRequester),
                        shape = RoundedCornerShape(4.dp),
                        value = inputUserName,
                        singleLine = true,
                        isError = isUserNameError,
                        onValueChange = { newValue ->
                            inputUserName = newValue.trim()
                            viewModel.inputUserName = newValue.trim()
                            isUserNameError = viewModel.checkIsUsernameTaken()
                        },
                        label = {
                            Text(text = "Username")
                        },
                        keyboardActions = KeyboardActions(onNext = {
                            userNameFocusManager.clearFocus(force = true)
                            emailFocusRequester.requestFocus()
                        }),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )
                    if (isUserNameError) {
                        Text(
                            text = "This username is already taken",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    //Email Text Field
                    OutlinedTextField(
                        modifier = Modifier
                            .scale(scaleY = 0.9F, scaleX = 1F)
                            .fillMaxWidth(fraction = 1F)
                            .focusRequester(emailFocusRequester),
                        shape = RoundedCornerShape(4.dp),
                        value = inputEmail,
                        singleLine = true,
                        isError = isEmailError,
                        onValueChange = { newValue ->
                            inputEmail = newValue.trim()
                            viewModel.inputEmail = newValue.trim()

                            isEmailError = viewModel.isInputEmailValid() is ValidityChecker.InValid
                        },
                        label = {
                            Text(text = "Email")
                        },
                        trailingIcon = {
                            if (isEmailError)
                                Icon(
                                    Icons.Filled.Info,
                                    "Error",
                                    tint = MaterialTheme.colorScheme.error
                                )
                        },
                        keyboardActions = KeyboardActions(onNext = {
                            emailFocusManager.clearFocus(force = true)
                        }),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )
                    if (isEmailError) {
                        Text(
                            text = viewModel.isInputEmailValid().message,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                    ) {

                        // Organizations Drop down
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val displayedText =
                                        if (selectedOrganizationPair.first.length <= 12) {
                                            selectedOrganizationPair.first.ifEmpty { "Organization" }
                                        } else {
                                            selectedOrganizationPair.first.take(12) + "..."
                                        }

                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = displayedText,
                                        maxLines = 1
                                    ).also {
                                        DropdownMenu(
                                            expanded = isOrganizationMenuExpanded,
                                            onDismissRequest = {
                                                isOrganizationMenuExpanded =
                                                    isOrganizationMenuExpanded.not()
                                            }) {

                                            organizationsPair.forEach { pair ->
                                                var isChecked: Boolean
                                                DropdownMenuItem(
                                                    interactionSource = MutableInteractionSource(),
                                                    onClick = {
                                                        viewModel.selectedOrganizationPair.value =
                                                            Pair(pair.first, pair.second)
                                                        isOrganizationMenuExpanded = false
                                                    }, text = {
                                                        Text(
                                                            maxLines = 1,
                                                            text = pair.first,
                                                            color = MaterialTheme.colorScheme.inverseSurface.copy(
                                                                alpha = .84F
                                                            )
                                                        )
                                                    },
                                                    trailingIcon = {
                                                        // compare ids
                                                        isChecked =
                                                            pair.second == selectedOrganizationPair.second

                                                        if (isChecked)
                                                            Icon(
                                                                modifier = Modifier.padding(start = 12.dp),
                                                                imageVector = Icons.Rounded.CheckCircle,
                                                                contentDescription = "Checked"
                                                            )
                                                    }
                                                )
                                            }
                                        }
                                    }

                                    IconButton(modifier = Modifier.size(34.dp),
                                        onClick = {
                                            isOrganizationMenuExpanded =
                                                isOrganizationMenuExpanded.not()
                                        }) {
                                        if (isOrganizationMenuExpanded) {
                                            Icon(
                                                imageVector = Icons.Default.KeyboardArrowUp,
                                                contentDescription = "arrow up"
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Default.KeyboardArrowDown,
                                                contentDescription = "arrow up"
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Roles Drop down
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val displayedText = if (selectedRolePair.first.length <= 12) {
                                        selectedRolePair.first.ifEmpty { "Role" }
                                    } else {
                                        selectedRolePair.first.take(12) + "..."
                                    }

                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = displayedText,
                                        maxLines = 1
                                    ).also {
                                        DropdownMenu(
                                            expanded = isRoleMenuExpanded,
                                            onDismissRequest = {
                                                isRoleMenuExpanded = isRoleMenuExpanded.not()
                                            }) {

                                            rolesPair.forEach { pair ->
                                                var isChecked: Boolean
                                                DropdownMenuItem(
                                                    interactionSource = MutableInteractionSource(),
                                                    onClick = {
                                                        viewModel.selectedRolePair.value =
                                                            Pair(pair.first, pair.second)
                                                        isRoleMenuExpanded = false
                                                    }, text = {
                                                        Text(
                                                            maxLines = 1,
                                                            text = pair.first,
                                                            color = MaterialTheme.colorScheme.inverseSurface.copy(
                                                                alpha = .84F
                                                            )
                                                        )
                                                    },
                                                    trailingIcon = {
                                                        // compare ids
                                                        isChecked =
                                                            pair.second == selectedRolePair.second

                                                        if (isChecked)
                                                            Icon(
                                                                modifier = Modifier.padding(start = 12.dp),
                                                                imageVector = Icons.Rounded.CheckCircle,
                                                                contentDescription = "Checked"
                                                            )
                                                    }
                                                )
                                            }
                                        }
                                    }

                                    IconButton(modifier = Modifier.size(34.dp),
                                        onClick = {
                                            isRoleMenuExpanded =
                                                isRoleMenuExpanded.not()
                                        }) {
                                        if (isRoleMenuExpanded) {
                                            Icon(
                                                imageVector = Icons.Default.KeyboardArrowUp,
                                                contentDescription = "arrow up"
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Default.KeyboardArrowDown,
                                                contentDescription = "arrow up"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    //Password Text Field
                    OutlinedTextField(
                        modifier = Modifier
                            .scale(scaleY = 0.9F, scaleX = 1F)
                            .fillMaxWidth(fraction = 1F)
                            .focusRequester(passwordFocusRequester),
                        shape = RoundedCornerShape(4.dp),
                        value = inputPassword,
                        singleLine = true,
                        isError = isPasswordError,
                        onValueChange = { newValue ->
                            inputPassword = newValue.trim()
                            viewModel.inputPassword = newValue.trim()
                            isPasswordValidMsg = viewModel.isInputPasswordValid().message
                            isPasswordError =
                                (viewModel.isInputPasswordValid() is ValidityChecker.InValid) or
                                        newValue.trim().isEmpty().also { empty ->
                                            isPasswordValidMsg = if (empty) {
                                                "Password is required"
                                            } else viewModel.isInputPasswordValid().message

                                            //Remove Errors shown on Confirm Password Field
                                            isConfirmPasswordError = false
                                        }
                        },
                        label = {
                            Text(text = "Password")
                        },
                        trailingIcon = {
                            val image = if (!isPasswordVisible)
                                R.drawable.ic_visibility else R.drawable.ic_visibility_off
                            IconButton(onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }) {
                                Icon(
                                    painter = painterResource(id = image),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = "Toggle Icon"
                                )
                            }
                        },
                        keyboardActions = KeyboardActions(onNext = {
                            passwordFocusManager.clearFocus(force = true)
                            confirmPasswordFocusRequester.requestFocus()
                        }),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )

                    if (isPasswordError) {
                        Text(
                            text = viewModel.isInputPasswordValid().message,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Confirm Password Text Field
                    OutlinedTextField(
                        modifier = Modifier
                            .scale(scaleY = 0.9F, scaleX = 1F)
                            .fillMaxWidth(fraction = 1F)
                            .focusRequester(confirmPasswordFocusRequester),
                        shape = RoundedCornerShape(4.dp),
                        value = inputConfirmPassword,
                        singleLine = true,
                        isError = isConfirmPasswordError,
                        onValueChange = { newValue ->
                            inputConfirmPassword = newValue.trim()
                            viewModel.inputConfirmPassword = newValue.trim()
                            isConfirmPasswordError = newValue.trim().isEmpty().also {
                                // Remove Errors shown on Password field
                                isPasswordError = false
                                isPasswordValidMsg = ""
                            }
                        },
                        label = {
                            Text(text = "Confirm Password")
                        },
                        trailingIcon = {
                            val image = if (!isPasswordVisible)
                                R.drawable.ic_visibility else R.drawable.ic_visibility_off
                            IconButton(onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }) {
                                Icon(
                                    painter = painterResource(id = image),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = "Toggle Icon"
                                )
                            }
                        },
                        keyboardActions = KeyboardActions(onDone = {
                            confirmPasswordFocusManager.clearFocus(force = true)
                        }),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )

                    if (isConfirmPasswordError) {
                        Text(
                            text = viewModel.isInputConfirmPasswordValid().message,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }


                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth(),
                    ) {
                        item {
                            Button(
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                                onClick = { }
                            ) {
                                Text(
                                    text = "CANCEL",
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }

                        item {
                            LaunchedEffect(key1 = authState) {
                                if (authState.error.isNotEmpty()) {
                                    Toast.makeText(context, authState.error, Toast.LENGTH_SHORT)
                                        .show()
                                }
                                if (authState.success) {
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT)
                                        .show()

                                    navigator?.popBackStack()
                                    navigator?.navigate(ProfileDestination)
                                }
                                if (authState.isLoading) {
                                    Toast.makeText(context, "Authenticating...", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }

                            Button(
                                shape = RoundedCornerShape(4.dp),
                                enabled = !authState.isLoading,
                                onClick = {
                                    viewModel.submitDetails()
                                }
                            ) {
                                Text(text = "SUBMIT")
                            }
                        }
                    }
                }
            }
        }
    }
}
