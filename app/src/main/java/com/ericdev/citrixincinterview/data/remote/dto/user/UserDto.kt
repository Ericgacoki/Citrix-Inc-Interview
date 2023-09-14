package com.ericdev.citrixincinterview.data.remote.dto.user


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("createdOn")
    val createdOn: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("organization")
    val organizationDto: UserOrganizationDto?,
    @SerializedName("roles")
    val rolesDto: List<UserRoleDto>?,
    @SerializedName("username")
    val username: String?
)
