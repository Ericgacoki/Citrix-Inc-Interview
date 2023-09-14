package com.ericdev.citrixincinterview.domain.usecase

import com.ericdev.citrixincinterview.domain.model.organization.OrganizationResult
import com.ericdev.citrixincinterview.domain.repository.MainRepository
import com.ericdev.citrixincinterview.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOrganizationsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(): Flow<Resource<OrganizationResult>> = flow {
        emit(Resource.Loading<OrganizationResult>())

        when (val organizations = mainRepository.getOrganizations()) {
            is Resource.Success -> emit(organizations)
            else -> {
                emit(Resource.Error<OrganizationResult>(message = organizations.message!!))
            }
        }
    }
}
