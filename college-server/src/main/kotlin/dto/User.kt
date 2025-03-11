package com.andreasmlbngaol.dto

import com.andreasmlbngaol.enums.Gender
import com.andreasmlbngaol.enums.Religion
import com.andreasmlbngaol.enums.Role
import kotlinx.serialization.Serializable

@Serializable
open class User(
    val id: Long,
    val email: String,
    val role: Role,
    val firstName: String?,
    val lastName: String?,
    val gender: Gender?,
    val religion: Religion?
) {
    val fullName: String
        get() = "$firstName $lastName"
}