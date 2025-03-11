package com.andreasmlbngaol.entity

import com.andreasmlbngaol.enums.AcademicRank
import com.andreasmlbngaol.enums.Major
import org.jetbrains.exposed.dao.id.LongIdTable

object TeacherEntity: LongIdTable("teachers") {
    val userId = reference("user_id", UserEntity)
    val teacherId = varchar("teacher_id", 50).uniqueIndex()
    val department = enumerationByName<Major>("department", 50)
    val academicRank = enumerationByName<AcademicRank>("academic_rank", 50)
}