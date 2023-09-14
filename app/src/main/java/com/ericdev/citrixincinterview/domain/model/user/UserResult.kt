package com.ericdev.citrixincinterview.domain.model.user

data class UserResult(
    val users: List<User>,
    val last: Boolean,
    val pageNo: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)
