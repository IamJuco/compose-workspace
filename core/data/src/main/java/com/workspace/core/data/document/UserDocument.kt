package com.workspace.core.data.document

data class UserDocument(
    val id: String = "",
    val email: String? = null,
    val nickName: String? = null,
    val profile: String? = null,
    val joinDate: Long = System.currentTimeMillis()
)