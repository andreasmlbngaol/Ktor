package com.andreasmlbngaol.dto.request

import com.andreasmlbngaol.enums.*
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused")
class TeacherRequest: UserRequest {
    val teacherId: String
    val department: Major
    val academicRank: AcademicRank

    constructor(
        email: String,
        realPassword: String,
        firstName: String? = null,
        lastName: String? = null,
        gender: Gender? = null,
        religion: Religion? = null,
        teacherId: String,
        department: Major,
        academicRank: AcademicRank
    ) : super(email, realPassword, Role.STUDENT, firstName, lastName, gender, religion) {
        this.teacherId = teacherId
        this.department = department
        this.academicRank = academicRank
    }
}
