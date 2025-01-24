package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(image: String): ServiceResult<String> {
        return userRepository.updateUserProfile(image)
    }
}