package com.ericdev.citrixincinterview.domain.model.organization

data class Organization(
    val active: Boolean,
    val contact: String,
    val createdOn: String,
    val email: String,
    val id: String,
    val organizationName: String,
    val organizationTypes: OrganizationTypes,
    val regions: Regions,
    val territories: Territories,
    val tin: String
)
