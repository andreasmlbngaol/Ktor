package com.andreasmlbngaol.dto

import com.andreasmlbngaol.enums.*
import kotlinx.serialization.Serializable

@Serializable
class Teacher: User {
    val teacherId: String
    val department: Major
    val academicRank: AcademicRank

    constructor(
        id: Long,
        email: String,
        role: Role,
        firstName: String?,
        lastName: String?,
        gender: Gender?,
        religion: Religion?,
        teacherId: String,
        department: Major,
        academicRank: AcademicRank
    ) : super(id, email, role, firstName, lastName, gender, religion) {
        this.teacherId = teacherId
        this.department = department
        this.academicRank = academicRank
    }
}