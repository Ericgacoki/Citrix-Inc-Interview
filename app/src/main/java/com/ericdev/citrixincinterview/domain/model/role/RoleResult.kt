package com.ericdev.citrixincinterview.domain.model.role


data class RoleResult(
    val roles: List<Role>,
    val last: Boolean,
    val pageNo: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)
