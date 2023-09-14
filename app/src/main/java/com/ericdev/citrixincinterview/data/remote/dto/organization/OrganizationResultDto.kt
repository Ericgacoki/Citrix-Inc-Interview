package com.ericdev.citrixincinterview.data.remote.dto.organization


import com.google.gson.annotations.SerializedName

data class OrganizationResultDto(
    @SerializedName("data")
    val organizations: List<OrganizationDto>?,
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
