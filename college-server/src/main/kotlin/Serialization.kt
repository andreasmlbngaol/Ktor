package com.andreasmlbngaol

import com.andreasmlbngaol.dao.UserDao
import com.andreasmlbngaol.dto.Student
import com.andreasmlbngaol.dto.Teacher
import com.andreasmlbngaol.dto.request.StudentRequest
import com.andreasmlbngaol.dto.request.TeacherRequest
import com.andreasmlbngaol.enums.Role
import com.andreasmlbngaol.utils.isEmail
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        route("/users") {

            get {
                val users = UserDao.getAllUsers()
                call.respond(users)
            }

            get("/role/{role}") {
                val roleAsText = call.parameters["role"]
                val studentId = call.queryParameters["student_id"]
                val teacherId = call.queryParameters["teacher_id"]

                if (roleAsText == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                if (studentId != null && teacherId != null || studentId?.isEmpty() == true || teacherId?.isEmpty() == true) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val role = Role.valueOf(roleAsText.uppercase())

                    if(studentId != null) {
                        val student = UserDao.getStudentByStudentId(studentId)

                        if(student == null) {
                            call.respond(HttpStatusCode.NotFound)
                            return@get
                        }

                        call.respond(student)
                        return@get
                    }

                    if(teacherId != null) {
                        val teacher = UserDao.getTeacherByTeacherId(teacherId)
                        if(teacher == null) {
                            call.respond(HttpStatusCode.NotFound)
                            return@get
                        }

                        call.respond(teacher)
                        return@get
                    }

                    val user = UserDao.getUsersByRole(role)
                    if(role == Role.STUDENT) call.respond(user.map { it as Student })
                    else call.respond(user.map { it as Teacher })
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                } catch (e: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
            }

            get("/id/{id}") {
                val idAsText = call.parameters["id"]
                if (idAsText == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val id = idAsText.toLong()

                    val user = UserDao.getUserById(id)

                    if(user == null) {
                        call.respond(HttpStatusCode.NotFound)
                        return@get
                    }

                    call.respond(user)
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
            }

            get("/email/{email}") {
                val email = call.parameters["email"]

                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val user = UserDao.getUserByEmail(email)
                if(user == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(user)
            }

            post("/role/{role}") {
                val roleAsText = call.parameters["role"]

                if(roleAsText == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                try {
                    val role = Role.valueOf(roleAsText.uppercase())

                    if(role == Role.STUDENT) {
                        val request = try {
                            call.receive<StudentRequest>()
                        } catch (e: ContentTransformationException) {
                            call.respond(HttpStatusCode.BadRequest, "Invalid JSON Format")
                            return@post
                        }

                        if (request.email.isBlank() || request.realPassword.isBlank()) {
                            call.respond(HttpStatusCode.BadRequest, "Email and password cannot be empty")
                            return@post
                        }
                        if (!request.email.isEmail()) {
                            call.respond(HttpStatusCode.BadRequest, "Email is invalid")
                            return@post
                        }
                        if (UserDao.getStudentByStudentId(request.studentId) != null) {
                            call.respond(HttpStatusCode.BadRequest, "Student ID already exists")
                            return@post
                        }

                        try {
                            UserDao.insertStudent(request)
                            call.respond(HttpStatusCode.Created, "Student successfully created")
                        } catch (e: Exception) {
                            println("Error inserting student: ${e.message}") // Debug
                            call.respond(HttpStatusCode.InternalServerError, "Student could not be created: ${e.message}")
                        }
                    }
                    else {
                        val request = try {
                            call.receive<TeacherRequest>()
                        } catch (e: ContentTransformationException) {
                            call.respond(HttpStatusCode.BadRequest, "Invalid JSON Format")
                            return@post
                        }

                        if (request.email.isBlank() || request.realPassword.isBlank()) {
                            call.respond(HttpStatusCode.BadRequest, "Email and password cannot be empty")
                            return@post
                        }
                        if (!request.email.isEmail()) {
                            call.respond(HttpStatusCode.BadRequest, "Email is invalid")
                            return@post
                        }
                        if (UserDao.getTeacherByTeacherId(request.teacherId) != null) {
                            call.respond(HttpStatusCode.BadRequest, "Teacher ID already exists")
                            return@post
                        }


                        try {
                            UserDao.insertTeacher(request)
                            call.respond(HttpStatusCode.Created, "Teacher successfully created")
                        } catch (e: Exception) {
                            call.respond(HttpStatusCode.InternalServerError, "Teacher could not be created: ${e.message}")
                        }
                    }
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
            }
        }
    }
}