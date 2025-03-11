package com.andreasmlbngaol.service

import at.favre.lib.crypto.bcrypt.BCrypt

object PasswordManager {
    private const val SALT_LENGTH = 12

    fun hashPassword(realPassword: String): String = BCrypt.withDefaults().hashToString(SALT_LENGTH, realPassword.toCharArray())

    @Suppress("unused")
    fun verifyPassword(inputPassword: String, hashedPassword: String): Boolean =
        BCrypt.verifyer().verify(hashedPassword.toCharArray(), inputPassword.toCharArray()).verified
}