package com.ericdev.citrixincinterview.presentation.state

import com.ericdev.citrixincinterview.domain.model.user.User

data class ClickedUserDetailsState(
    val isLoading: Boolean = false,
    val userDetails: User? = null,
    val error: String = ""
)
