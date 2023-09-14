package com.ericdev.citrixincinterview.data.repository

import com.ericdev.citrixincinterview.data.local.database.CitrixAppDatabase
import com.ericdev.citrixincinterview.data.local.entity.UserEntity
import com.ericdev.citrixincinterview.data.mapper.*
import com.ericdev.citrixincinterview.data.remote.api.ApiService
import com.ericdev.citrixincinterview.domain.model.organization.OrganizationResult
import com.ericdev.citrixincinterview.domain.model.role.RoleResult
import com.ericdev.citrixincinterview.domain.model.user.CreateUserRequest
import com.ericdev.citrixincinterview.domain.model.user.LoggedInUser
import com.ericdev.citrixincinterview.domain.model.user.User
import com.ericdev.citrixincinterview.domain.model.user.UserResult
import com.ericdev.citrixincinterview.domain.repository.MainRepository
import com.ericdev.citrixincinterview.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MainDataRepository @Inject constructor(
    private val apiService: ApiService,
    private val citrixAppDatabase: CitrixAppDatabase
) : MainRepository {

    override suspend fun getOrganizations(): Resource<OrganizationResult> {
        return try {
            val remoteOrganizationResult = apiService.getOrganizations().toOrganizationResult()
            Resource.Success<OrganizationResult>(data = remoteOrganizationResult)
        } catch (e: IOException) {
            Resource.Error<OrganizationResult>(message = "Unable to fetch organizations. Check your internet connection!")

        } catch (e: HttpException) {
            Resource.Error<OrganizationResult>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }

    override suspend fun getRoles(): Resource<RoleResult> {
        return try {
            val remoteRoleResult = apiService.getRoles().toRoleResult()

            Resource.Success<RoleResult>(data = remoteRoleResult)
        } catch (e: IOException) {
            Resource.Error<RoleResult>(message = "Unable to fetch roles. Check your internet connection!")

        } catch (e: HttpException) {
            Resource.Error<RoleResult>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }

    override suspend fun getAllUsers(): Resource<UserResult> {
        return try {
            /*val localUserResult = UserResult(
                users = citrixAppDatabase.localUsersDao.getLocalUsers().map { it.toUser() },
                last = true,
                pageNo = 1,
                pageSize = 0,
                totalElements = 0,
                totalPages = 1
            )*/

            val remoteUsers = apiService.getAllUsers().toUserResult()
            citrixAppDatabase.localUsersDao.addLocalUsers(remoteUsers.toUserEntity())
            Resource.Success<UserResult>(data = remoteUsers)

        } catch (e: IOException) {

            Resource.Error<UserResult>(message = "Unable to fetch users. Check your internet connection!")
        } catch (e: HttpException) {

            Resource.Error<UserResult>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }

    override suspend fun getUserDetails(id: String): Resource<User> {
        return try {
            val userDetails = apiService.getUserDetails(id)
            Resource.Success<User>(data = userDetails.toUser())
        } catch (e: IOException) {

            Resource.Error<User>(message = "Unable to fetch user details.\nCheck your internet connection!")
        } catch (e: HttpException) {

            Resource.Error<User>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }

    override suspend fun createUser(newUser: CreateUserRequest): Resource<User> {
        return try {
            val user = apiService.createUser(newUser).toUser()
            Resource.Success<User>(data = user)
        } catch (e: IOException) {
            Resource.Error<User>(message = "Unable create account. Check your internet connection!")

        } catch (e: HttpException) {
            Resource.Error<User>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }

    override suspend fun saveLoggedInUser(loggedInUser: LoggedInUser) {
        citrixAppDatabase.loggedInUserDao.insertLoggedInUser(loggedInUser.toLoggedUserEntity())
    }

    override suspend fun getLoggedInUser(): LoggedInUser? {
        var loggedInUser: LoggedInUser? = null

        try {
            loggedInUser = citrixAppDatabase.loggedInUserDao.getLoggedInUser()?.toLoggedInUser()
        } catch (e: IOException) {
            Timber.e("UNABLE TO GET LOGGED IN USER")
        }

        return loggedInUser
    }

    override fun getLocalUserById(id: String): Flow<Resource<List<UserEntity>>> {
        return citrixAppDatabase.localUsersDao.getLocalUserById(id)
            .map { users ->
                if (users.isNotEmpty()) {
                    Resource.Success(data = users)
                } else {
                    Resource.Error(message = "No users found")
                }
            }
            .onStart { emit(Resource.Loading()) }
            .catch { e -> emit(Resource.Error(message = e.message ?: "An error occurred")) }
    }
}
