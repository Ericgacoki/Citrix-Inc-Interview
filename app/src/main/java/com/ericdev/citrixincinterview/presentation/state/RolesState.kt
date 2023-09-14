package com.ericdev.citrixincinterview.presentation.state

import com.ericdev.citrixincinterview.domain.model.role.Role

data class RolesState(
    val isLoading: Boolean = false,
    val roles: List<Role> = emptyList(),
    val error: String = ""
)
