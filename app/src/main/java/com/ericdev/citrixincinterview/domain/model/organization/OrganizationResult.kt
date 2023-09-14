package com.ericdev.citrixincinterview.domain.model.organization


data class OrganizationResult(
    val organizations: List<Organization>,
    val last: Boolean,
    val pageNo: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)
