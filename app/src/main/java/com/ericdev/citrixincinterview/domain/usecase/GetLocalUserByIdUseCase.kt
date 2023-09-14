package com.ericdev.citrixincinterview.domain.usecase

import com.ericdev.citrixincinterview.data.local.entity.UserEntity
import com.ericdev.citrixincinterview.domain.repository.MainRepository
import com.ericdev.citrixincinterview.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalUserByIdUseCase @Inject constructor(
    val mainRepository: MainRepository
) {
    operator fun invoke(id: String): Flow<Resource<List<UserEntity>>> {
        return mainRepository.getLocalUserById(id)
    }
}
