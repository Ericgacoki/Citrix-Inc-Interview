package com.ericdev.citrixincinterview.domain.usecase

import com.ericdev.citrixincinterview.domain.model.user.LoggedInUser
import com.ericdev.citrixincinterview.domain.repository.MainRepository
import javax.inject.Inject

class GetLoggedInUserUseCase  @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(): LoggedInUser? {
        return mainRepository.getLoggedInUser()
    }
}
