package com.ericdev.citrixincinterview.domain.model.user

data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val username: String,
    val password: String,
    val confirmPassword: String,
    val email: String,
    val organizationId: String,
    val roleId: String
)
