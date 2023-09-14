package com.ericdev.citrixincinterview.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericdev.citrixincinterview.data.mapper.toUser
import com.ericdev.citrixincinterview.domain.model.user.CreateUserRequest
import com.ericdev.citrixincinterview.domain.model.user.LoggedInUser
import com.ericdev.citrixincinterview.domain.usecase.*
import com.ericdev.citrixincinterview.presentation.state.*
import com.ericdev.citrixincinterview.util.Resource
import com.ericdev.validitychecker.ValidityChecker
import com.ericdev.validitychecker.ValidityChecker.Companion.isValidEmail
import com.ericdev.validitychecker.ValidityChecker.Companion.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val getOrganizationsUseCase: GetOrganizationsUseCase,
    val getRolesUseCase: GetRolesUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val createUsersUseCase: CreateUserUseCase,
    val getLoggedInUserUseCase: GetLoggedInUserUseCase,
    val saveLoggedInUserUseCase: SaveLoggedInUserUseCase,
    val getLocalUserByIdUseCase: GetLocalUserByIdUseCase,
    val getClickedUserDetailsUseCase: GetClickedUserDetailsUseCase
) : ViewModel() {
    // User inputs
    var inputFirstName = ""
    var inputLastName = ""

    var inputUserName = ""
    var takenUsernames = emptyList<String>()
    var inputEmail = ""

    var inputPassword = ""
    var inputConfirmPassword = ""

    var selectedOrganizationPair: MutableStateFlow<Pair<String, String>> =
        MutableStateFlow(Pair("", ""))
    var selectedRolePair: MutableStateFlow<Pair<String, String>> = MutableStateFlow(Pair("", ""))

    private val _organizationState: MutableStateFlow<OrganizationState> =
        MutableStateFlow(OrganizationState())
    var organizationState: StateFlow<OrganizationState> = _organizationState.asStateFlow()
        private set

    var organizationsPair: MutableStateFlow<List<Pair<String, String>>> =
        MutableStateFlow(emptyList())
        private set

    private val _rolesState: MutableStateFlow<RolesState> = MutableStateFlow(RolesState())
    var rolesState: StateFlow<RolesState> = _rolesState.asStateFlow()
        private set

    private val _loggedInUserId: MutableStateFlow<String?> = MutableStateFlow(null)
    var loggedInUserId: StateFlow<String?> = _loggedInUserId.asStateFlow()
        private set

    private val _clickedUserDetailsState: MutableStateFlow<ClickedUserDetailsState> =
        MutableStateFlow(ClickedUserDetailsState())
    var clickedUserDetailsState: StateFlow<ClickedUserDetailsState> =
        _clickedUserDetailsState.asStateFlow()
        private set

    private val _localUserProfileState: MutableStateFlow<LocalUserProfileState> =
        MutableStateFlow(LocalUserProfileState())
    var localUserProfileState: StateFlow<LocalUserProfileState> =
        _localUserProfileState.asStateFlow()
        private set

    private val _allUsersState: MutableStateFlow<AllUsersState> = MutableStateFlow(AllUsersState())
    var allUsersState: StateFlow<AllUsersState> = _allUsersState.asStateFlow()
        private set

    private val _authState: MutableState<AuthState> = mutableStateOf(AuthState())
    var authState: State<AuthState> = _authState
        private set

    var rolesPair: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(emptyList())
        private set

    fun isInputEmailValid(): ValidityChecker {
        return isValidEmail(inputEmail)
    }

    fun isInputPasswordValid(): ValidityChecker {
        return isValidPassword(inputPassword)
    }

    fun isInputConfirmPasswordValid(): ValidityChecker {
        return isValidPassword(inputConfirmPassword)
    }

    fun checkIsUsernameTaken(): Boolean {
        return takenUsernames.contains(inputUserName)
    }

    init {
        getLoggedInUserId()
        getOrganizations()
        getRoles()
        getAllUsers()
        getLocalUserById()
    }

    fun retryFetchingData() {
        getLoggedInUserId()
        getOrganizations()
        getRoles()
        getAllUsers()
        getLocalUserById()
    }

    private fun getLoggedInUserId() {
        viewModelScope.launch {
            _loggedInUserId.value = getLoggedInUserUseCase()?.id
        }
    }

    fun getLocalUserById() {
        viewModelScope.launch {
            val userId = getLoggedInUserUseCase()?.id ?: ""

            getLocalUserByIdUseCase(userId).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _localUserProfileState.value = LocalUserProfileState(
                            isLoading = true,
                            localUser = null,
                            error = "",
                        )
                    }
                    is Resource.Error -> {
                        _localUserProfileState.value = LocalUserProfileState(
                            isLoading = false,
                            localUser = null,
                            error = result.message!!
                        )
                    }
                    is Resource.Success -> {
                        _localUserProfileState.value = LocalUserProfileState(
                            isLoading = false,
                            localUser = result.data?.get(0)?.toUser(),
                            error = ""
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getClickedUserDetails(id: String) {
        getClickedUserDetailsUseCase(id).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _clickedUserDetailsState.value = ClickedUserDetailsState(isLoading = true)
                }
                is Resource.Error -> {
                    _clickedUserDetailsState.value = ClickedUserDetailsState(
                        isLoading = false,
                        userDetails = null,
                        error = result.message!!
                    )
                }
                is Resource.Success -> {
                    _clickedUserDetailsState.value = ClickedUserDetailsState(
                        isLoading = false,
                        userDetails = result.data,
                        error = ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getOrganizations() {
        getOrganizationsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val organizations = result.data?.organizations ?: emptyList()
                    _organizationState.value = OrganizationState(organizations = organizations)

                    // Create a pair of name and id of all organizations
                    // This is then shown in the dropdown menu
                    organizationsPair.emit(organizations.map { Pair(it.organizationName, it.id) })
                }
                is Resource.Error -> {
                    _organizationState.value = OrganizationState(error = result.message!!)
                }
                is Resource.Loading -> {
                    _organizationState.value = OrganizationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRoles() {
        getRolesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val roles = result.data?.roles ?: emptyList()
                    _rolesState.value = RolesState(roles = roles)

                    // Create a pair of name and id of all roles
                    // This is then shown in the dropdown menu
                    rolesPair.emit(roles.map { Pair(it.name, it.id) })
                }
                is Resource.Error -> {
                    _rolesState.value = RolesState(error = result.message!!)
                }
                is Resource.Loading -> {
                    _rolesState.value = RolesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAllUsers() {
        getAllUsersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val users = result.data?.users ?: emptyList()
                    _allUsersState.value = AllUsersState(users = users)

                    takenUsernames = result.data?.users?.map { it.username } ?: emptyList()
                    Timber.e("TAKEN USERNAMES: $takenUsernames")
                }
                is Resource.Loading -> {
                    _allUsersState.value = AllUsersState(isLoading = true)
                }
                is Resource.Error -> {
                    _allUsersState.value = AllUsersState(error = result.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun createNewUser(newUser: CreateUserRequest) {
        createUsersUseCase(newUser).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val user = result.data!! // This must NOT be null by any chance

                    // Add the new user to local database
                    saveLoggedInUserUseCase(LoggedInUser(user.id))
                    _loggedInUserId.value = user.id

                    _authState.value = AuthState(success = true)
                }
                is Resource.Loading -> {
                    Timber.i("CREATING NEW USER")
                    _authState.value = AuthState(isLoading = true)
                }
                is Resource.Error -> {
                    Timber.i("ERROR CREATING USER")
                    _authState.value = AuthState(error = "Authentication failed!")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun submitDetails() {
        if (inputFirstName.isNotEmpty() &&
            inputLastName.isNotEmpty() &&
            inputUserName.isNotEmpty() &&
            !checkIsUsernameTaken() &&
            selectedOrganizationPair.value.first.isNotEmpty() &&
            selectedRolePair.value.first.isNotEmpty() &&
            isInputPasswordValid() is ValidityChecker.Valid && isInputEmailValid() is ValidityChecker.Valid
        ) {
            if (inputPassword == inputConfirmPassword) {
                // Now we have everything we need to create a user
                val newUser = CreateUserRequest(
                    firstName = inputFirstName,
                    lastName = inputLastName,
                    username = inputUserName,
                    password = inputPassword,
                    confirmPassword = inputConfirmPassword,
                    email = inputEmail,
                    organizationId = selectedOrganizationPair.value.second,
                    roleId = selectedRolePair.value.second
                )
                createNewUser(newUser)
            } else {
                _authState.value = AuthState(error = "Passwords mismatch")
            }
        } else if (
            inputFirstName.isEmpty() or
            inputLastName.isEmpty() or
            inputUserName.isEmpty()
        ) {
            _authState.value = _authState.value.copy(error = "All fields are required")
        } else if (
            selectedOrganizationPair.value.first.isEmpty() or
            selectedRolePair.value.first.isEmpty()
        ) {
            _authState.value = _authState.value.copy(error = "Select organization and role")
        } else {
            // Most of this part is already handled by the real-time UI updates
            _authState.value = _authState.value.copy(error = "Fill all fields correctly")
        }
    }
}
