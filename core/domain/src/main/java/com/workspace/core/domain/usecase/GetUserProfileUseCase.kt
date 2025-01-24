package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.repository.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ServiceResult<User> {
        return userRepository.getUserInfo()
    }
}