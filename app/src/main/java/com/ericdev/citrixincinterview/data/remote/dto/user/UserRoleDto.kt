package com.ericdev.citrixincinterview.data.remote.dto.user


import com.google.gson.annotations.SerializedName

data class UserRoleDto(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("createdOn")
    val createdOn: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)
