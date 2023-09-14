package com.ericdev.citrixincinterview.domain.usecase

import com.ericdev.citrixincinterview.domain.model.role.RoleResult
import com.ericdev.citrixincinterview.domain.repository.MainRepository
import com.ericdev.citrixincinterview.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRolesUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(): Flow<Resource<RoleResult>> = flow {
        emit(Resource.Loading<RoleResult>())

        when (val roles = mainRepository.getRoles()) {
            is Resource.Success -> emit(roles)
            else -> {
                emit(Resource.Error<RoleResult>(message = roles.message!!))
            }
        }
    }
}
