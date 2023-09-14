package com.ericdev.citrixincinterview.domain.usecase

import com.ericdev.citrixincinterview.domain.model.user.LoggedInUser
import com.ericdev.citrixincinterview.domain.repository.MainRepository
import javax.inject.Inject

class SaveLoggedInUserUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(loggedInUser: LoggedInUser) {
        mainRepository.saveLoggedInUser(loggedInUser)
    }
}
