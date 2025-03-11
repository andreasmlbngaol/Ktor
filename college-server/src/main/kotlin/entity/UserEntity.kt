package com.andreasmlbngaol.entity

import com.andreasmlbngaol.enums.Gender
import com.andreasmlbngaol.enums.Religion
import com.andreasmlbngaol.enums.Role
import org.jetbrains.exposed.dao.id.LongIdTable

object UserEntity: LongIdTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val role = enumerationByName<Role>("role", 50)
    val firstName = varchar("first_name", 255).nullable()
    val lastName = varchar("last_name", 255).nullable()
    val gender = enumerationByName<Gender>("gender", 50).nullable()
    val religion = enumerationByName<Religion>("religion", 50).nullable()
}