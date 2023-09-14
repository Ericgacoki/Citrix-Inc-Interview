package com.ericdev.citrixincinterview.data.remote.dto.organization


import com.google.gson.annotations.SerializedName

data class TerritoriesDto(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("createdOn")
    val createdOn: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("territoryName")
    val territoryName: String?
)