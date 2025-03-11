package com.andreasmlbngaol.dao

import com.andreasmlbngaol.dto.Student
import com.andreasmlbngaol.dto.Teacher
import com.andreasmlbngaol.dto.request.StudentRequest
import com.andreasmlbngaol.dto.request.TeacherRequest
import com.andreasmlbngaol.dto.request.UserRequest
import com.andreasmlbngaol.entity.StudentEntity
import com.andreasmlbngaol.entity.TeacherEntity
import com.andreasmlbngaol.entity.UserEntity
import com.andreasmlbngaol.enums.Role
import com.andreasmlbngaol.service.PasswordManager
import com.andreasmlbngaol.service.toDTO
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object UserDao {
    fun getAllUsers() = transaction {
        (UserEntity leftJoin StudentEntity leftJoin TeacherEntity)
            .selectAll()
            .map { it.toDTO() }
    }

    fun getUsersByRole(role: Role) = transaction {
        (UserEntity leftJoin StudentEntity leftJoin TeacherEntity)
            .selectAll()
            .where { UserEntity.role eq role }
            .map { it.toDTO() }
    }

    fun getUserById(id: Long) = transaction {
        (UserEntity leftJoin StudentEntity leftJoin TeacherEntity)
            .selectAll()
            .where { UserEntity.id eq id }
            .mapNotNull { it.toDTO() }
            .singleOrNull()
    }

    fun getUserByEmail(email: String) = transaction {
        (UserEntity leftJoin StudentEntity leftJoin TeacherEntity)
            .selectAll()
            .where { UserEntity.email eq email }
            .mapNotNull { it.toDTO() }
            .singleOrNull()
    }

    fun getStudentByStudentId(studentId: String) = transaction {
        (UserEntity innerJoin StudentEntity)
            .selectAll()
            .where { StudentEntity.studentId eq studentId }
            .mapNotNull { it.toDTO() as Student }
            .singleOrNull()
    }

    fun getTeacherByTeacherId(teacherId: String) = transaction {
        (UserEntity innerJoin TeacherEntity)
            .selectAll()
            .where {TeacherEntity.teacherId eq teacherId }
            .mapNotNull { it.toDTO() as Teacher }
            .singleOrNull()
    }

    fun insertStudent(student: StudentRequest) = transaction {
        val userId = insertUserAndGetId(student)

        StudentEntity.insert {
            it[StudentEntity.userId] = userId
            it[studentId] = student.studentId
            it[major] = student.major
            it[gpa] = student.gpa
        }
    }

    fun insertTeacher(teacher: TeacherRequest) = transaction {
        val userId = insertUserAndGetId(teacher)

        TeacherEntity.insert {
            it[TeacherEntity.userId] = userId
            it[teacherId] = teacher.teacherId
            it[department] = teacher.department
            it[academicRank] = teacher.academicRank
        }
    }

    private fun insertUserAndGetId(user: UserRequest) = UserEntity.insertAndGetId {
        it[email] = user.email
        it[passwordHash] = PasswordManager.hashPassword(user.realPassword)
        it[role] = user.role
        it[firstName] = user.firstName
        it[lastName] = user.lastName
        it[gender] = user.gender
        it[religion] = user.religion
    }.value
}