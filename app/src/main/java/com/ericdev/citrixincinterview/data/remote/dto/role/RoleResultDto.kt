package com.ericdev.citrixincinterview.data.remote.dto.role

import com.google.gson.annotations.SerializedName

data class RoleResultDto(
    @SerializedName("data")
    val rolesDto: List<RoleDto>?,
    @SerializedName("last")
    val last: Boolean?,
    @SerializedName("pageNo")
    val pageNo: Int?,
    @SerializedName("pageSize")
    val pageSize: Int?,
    @SerializedName("totalElements")
    val totalElements: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?
)
