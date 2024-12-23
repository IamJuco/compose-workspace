package com.workspace.core.domain.utils

object TempPassword {
    private const val PASSWORD_LENGTH = 12
    private const val CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*\\"

    fun tempRandomPassword(): String {
        return (1..PASSWORD_LENGTH)
            .map { CHAR_POOL.random() }
            .joinToString("")
    }
}