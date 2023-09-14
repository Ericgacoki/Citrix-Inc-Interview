package com.ericdev.citrixincinterview.data.remote.api

import com.ericdev.citrixincinterview.data.remote.dto.organization.OrganizationResultDto
import com.ericdev.citrixincinterview.data.remote.dto.role.RoleResultDto
import com.ericdev.citrixincinterview.data.remote.dto.user.UserDto
import com.ericdev.citrixincinterview.data.remote.dto.user.UserResultDto
import com.ericdev.citrixincinterview.domain.model.user.CreateUserRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("organization")
    suspend fun getOrganizations(): OrganizationResultDto

    @GET("role")
    suspend fun getRoles(): RoleResultDto

    @GET("user")
    suspend fun getAllUsers(): UserResultDto

    @GET("user/{userId}")
    suspend fun getUserDetails(
        @Path("userId") id: String
    ): UserDto

    @POST("user")
    suspend fun createUser(@Body request: CreateUserRequest): UserDto
}
