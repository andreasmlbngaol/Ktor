package com.andreasmlbngaol.dto

import com.andreasmlbngaol.enums.Gender
import com.andreasmlbngaol.enums.Major
import com.andreasmlbngaol.enums.Religion
import com.andreasmlbngaol.enums.Role
import kotlinx.serialization.Serializable

@Serializable
class Student : User {
    val studentId: String
    val major: Major
    val gpa: Double

    constructor(
        id: Long,
        email: String,
        role: Role,
        firstName: String?,
        lastName: String?,
        gender: Gender?,
        religion: Religion?,
        studentId: String,
        major: Major,
        gpa: Double
    ) : super(id, email, role, firstName, lastName, gender, religion) {
        this.studentId = studentId
        this.major = major
        this.gpa = gpa
    }
}
