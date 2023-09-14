package com.ericdev.citrixincinterview.domain.usecase

import com.ericdev.citrixincinterview.domain.model.user.UserResult
import com.ericdev.citrixincinterview.domain.repository.MainRepository
import com.ericdev.citrixincinterview.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(): Flow<Resource<UserResult>> = flow {
        emit(Resource.Loading<UserResult>())

        when (val users = mainRepository.getAllUsers()) {
            is Resource.Success -> emit(users)
            else -> {
                emit(Resource.Error<UserResult>(message = users.message!!))
            }
        }
    }
}
