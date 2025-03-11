package com.andreasmlbngaol.entity

import com.andreasmlbngaol.enums.Major
import org.jetbrains.exposed.dao.id.LongIdTable

object StudentEntity: LongIdTable("students") {
    val userId = reference("user_id", UserEntity)
    val studentId = varchar("student_id", 50).uniqueIndex()
    val major = enumerationByName<Major>("major", 50)
    val gpa = double("gpa").default(0.0)
}