package com.ericdev.citrixincinterview.data.remote.dto.user


import com.google.gson.annotations.SerializedName

data class UserOrganizationDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("organizationName")
    val organizationName: String?
)
