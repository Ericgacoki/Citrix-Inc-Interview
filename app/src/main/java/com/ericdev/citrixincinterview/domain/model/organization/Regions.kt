package com.ericdev.citrixincinterview.domain.model.organization


import com.google.gson.annotations.SerializedName

data class Regions(
    val active: Boolean,
    val createdOn: String,
    val id: String,
    val regionName: String
)
