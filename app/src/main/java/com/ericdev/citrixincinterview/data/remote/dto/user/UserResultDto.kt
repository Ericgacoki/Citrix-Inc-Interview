package com.ericdev.citrixincinterview.data.remote.dto.user


import com.google.gson.annotations.SerializedName

data class UserResultDto(
    @SerializedName("data")
    val userDto: List<UserDto>?,
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
