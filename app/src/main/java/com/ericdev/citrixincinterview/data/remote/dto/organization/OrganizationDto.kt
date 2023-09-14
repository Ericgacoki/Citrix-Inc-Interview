package com.ericdev.citrixincinterview.data.remote.dto.organization


import com.google.gson.annotations.SerializedName

data class OrganizationDto(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("contact")
    val contact: String?,
    @SerializedName("createdOn")
    val createdOn: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("organizationName")
    val organizationName: String?,
    @SerializedName("organizationTypes")
    val organizationTypesDto: OrganizationTypesDto?,
    @SerializedName("regions")
    val regionsDto: RegionsDto?,
    @SerializedName("territories")
    val territoriesDto: TerritoriesDto?,
    @SerializedName("tin")
    val tin: String?
)
