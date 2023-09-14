package com.ericdev.citrixincinterview.presentation.state

import com.ericdev.citrixincinterview.domain.model.user.User

data class AllUsersState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String = ""
)
