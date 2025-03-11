package com.andreasmlbngaol.service

import com.andreasmlbngaol.entity.UserEntity
import com.andreasmlbngaol.dto.Student
import com.andreasmlbngaol.dto.Teacher
import com.andreasmlbngaol.dto.User
import com.andreasmlbngaol.entity.StudentEntity
import com.andreasmlbngaol.entity.TeacherEntity
import com.andreasmlbngaol.enums.Role
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toDTO(): User {
    val role = this[UserEntity.role]

    return when (role) {
        Role.STUDENT -> Student(
            id = this[UserEntity.id].value,
            email = this[UserEntity.email],
            role = this[UserEntity.role],
            firstName = this[UserEntity.firstName],
            lastName = this[UserEntity.lastName],
            gender = this[UserEntity.gender],
            religion = this[UserEntity.religion],
            studentId = this[StudentEntity.studentId],
            major = this[StudentEntity.major],
            gpa = this[StudentEntity.gpa],
        )

        Role.TEACHER -> Teacher(
            id = this[UserEntity.id].value,
            email = this[UserEntity.email],
            role = this[UserEntity.role],
            firstName = this[UserEntity.firstName],
            lastName = this[UserEntity.lastName],
            gender = this[UserEntity.gender],
            religion = this[UserEntity.religion],
            teacherId = this[TeacherEntity.teacherId],
            department = this[TeacherEntity.department],
            academicRank = this[TeacherEntity.academicRank]
        )

//        else -> User(
//            id = this[UserEntity.id].value,
//            email = this[UserEntity.email],
//            role = this[UserEntity.role],
//            firstName = this[UserEntity.firstName],
//            lastName = this[UserEntity.lastName],
//            gender = this[UserEntity.gender],
//            religion = this[UserEntity.religion],
//        )
    }
}