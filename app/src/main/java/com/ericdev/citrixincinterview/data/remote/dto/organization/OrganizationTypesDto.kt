package com.ericdev.citrixincinterview.data.remote.dto.organization


import com.google.gson.annotations.SerializedName

data class OrganizationTypesDto(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("createdOn")
    val createdOn: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("organizationType")
    val organizationType: String?
)