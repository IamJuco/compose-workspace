package com.workspace.core.data.mapper

import com.workspace.core.data.document.UserDocument
import com.workspace.core.domain.model.User

fun User.toDocument(): UserDocument {
    return UserDocument(
        id = id,
        email = email,
        nickName = nickName,
        profile = profile
    )
}

fun UserDocument.toDomain(): User {
    return User(
        id = id,
        email = email,
        nickName = nickName,
        profile = profile
    )
}