package com.andreasmlbngaol.dto.request

import com.andreasmlbngaol.enums.Gender
import com.andreasmlbngaol.enums.Religion
import com.andreasmlbngaol.enums.Role
import kotlinx.serialization.Serializable

@Serializable
open class UserRequest(
    val email: String,
    val realPassword: String,
    val role: Role,
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: Gender? = null,
    val religion: Religion? = null
)