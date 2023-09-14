package com.ericdev.citrixincinterview.domain.model.user

data class User(
    val active: Boolean,
    val createdOn: String,
    val email: String,
    val firstName: String,
    val id: String = "",
    val lastName: String,
    val organization: UserOrganization,
    val roles: List<UserRole>,
    val username: String
)
