package com.ericdev.citrixincinterview.presentation.state

import com.ericdev.citrixincinterview.domain.model.user.User

data class LocalUserProfileState(
    val isLoading: Boolean = false,
    val localUser: User? = null,
    val error: String = ""
)
