package com.andreasmlbngaol.dto.request

import com.andreasmlbngaol.enums.Gender
import com.andreasmlbngaol.enums.Major
import com.andreasmlbngaol.enums.Religion
import com.andreasmlbngaol.enums.Role
import kotlinx.serialization.Serializable

@Serializable
class StudentRequest: UserRequest {
    val  studentId: String
    val major: Major
    val gpa: Double

    constructor(
        email: String,
        realPassword: String,
        firstName: String? = null,
        lastName: String? = null,
        gender: Gender? = null,
        religion: Religion? = null,
        studentId: String,
        major: Major,
        gpa: Double = 0.0
    ) : super(email, realPassword, Role.STUDENT, firstName, lastName, gender, religion) {
        this.studentId = studentId
        this.major = major
        this.gpa = gpa
    }
}
