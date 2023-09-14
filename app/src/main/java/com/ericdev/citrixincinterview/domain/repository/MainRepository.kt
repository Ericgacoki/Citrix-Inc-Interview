package com.ericdev.citrixincinterview.domain.repository

import com.ericdev.citrixincinterview.data.local.entity.UserEntity
import com.ericdev.citrixincinterview.domain.model.organization.OrganizationResult
import com.ericdev.citrixincinterview.domain.model.role.RoleResult
import com.ericdev.citrixincinterview.domain.model.user.CreateUserRequest
import com.ericdev.citrixincinterview.domain.model.user.LoggedInUser
import com.ericdev.citrixincinterview.domain.model.user.User
import com.ericdev.citrixincinterview.domain.model.user.UserResult
import com.ericdev.citrixincinterview.util.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getOrganizations(): Resource<OrganizationResult>

    suspend fun getRoles(): Resource<RoleResult>

    suspend fun getAllUsers(): Resource<UserResult>

    suspend fun getUserDetails(id: String): Resource<User>

    suspend fun createUser(newUser: CreateUserRequest): Resource<User>

    suspend fun getLoggedInUser(): LoggedInUser?

    suspend fun saveLoggedInUser(loggedInUser: LoggedInUser)

    fun getLocalUserById(id: String): Flow<Resource<List<UserEntity>>>
}
