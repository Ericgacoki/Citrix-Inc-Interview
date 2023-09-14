package com.ericdev.citrixincinterview.presentation.state

import com.ericdev.citrixincinterview.domain.model.organization.Organization

class OrganizationState (
    val isLoading: Boolean = false,
    val organizations: List<Organization> = emptyList(),
    val error: String = ""
)
