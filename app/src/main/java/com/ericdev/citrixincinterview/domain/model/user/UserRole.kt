package com.ericdev.citrixincinterview.domain.model.user

data class UserRole(
    val active: Boolean,
    val createdOn: String,
    val description: String,
    val id: String,
    val name: String
)
