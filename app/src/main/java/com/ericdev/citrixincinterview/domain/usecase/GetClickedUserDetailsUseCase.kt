package com.ericdev.citrixincinterview.domain.usecase

import com.ericdev.citrixincinterview.domain.model.user.User
import com.ericdev.citrixincinterview.domain.repository.MainRepository
import com.ericdev.citrixincinterview.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetClickedUserDetailsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(id: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading<User>())

        when (val user = mainRepository.getUserDetails(id)) {
            is Resource.Success -> emit(user)
            else -> {
                emit(Resource.Error<User>(message = user.message!!))
            }
        }
    }
}