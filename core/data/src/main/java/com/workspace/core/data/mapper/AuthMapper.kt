package com.workspace.core.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.workspace.core.domain.model.User

fun FirebaseUser.toDomainModel(): User {
    return User(
        id = uid,
        email = email,
        nickName = displayName,
        profile = photoUrl?.toString(),
    )
}