import com.andreasmlbngaol.db.DatabaseFactory
import com.andreasmlbngaol.dto.User
import com.andreasmlbngaol.dto.request.StudentRequest
import com.andreasmlbngaol.enums.Gender
import com.andreasmlbngaol.enums.Major
import com.andreasmlbngaol.enums.Religion
import com.andreasmlbngaol.enums.Role
import com.andreasmlbngaol.module
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertTrue
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializationKtTest {

    @Test
    fun testGetUsers() = testApplication {
        application {
            DatabaseFactory.init(environment.config)
            module()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val response = client.get("/users")
        val results = response.body<List<User>>()

        assertEquals(HttpStatusCode.OK, response.status)

        val expectedUser = User(
            id = 1,
            email = "john.doe@example.com",
            role = Role.STUDENT,
            firstName = "John",
            lastName = "Doe",
            gender = Gender.MALE,
            religion = Religion.PROTESTANT
        )

        assertTrue(
            results
                .map { it.fullName }
                .any { it == expectedUser.fullName }
        )

    }

    @Test
    fun testPostUsersRoleRole() = testApplication {
        application {
            module()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val initialUserSize = client.get("/users").body<List<User>>().size

        val student = StudentRequest(
            email = "meylisafernanda15@gmail.com",
            realPassword = "password123",
            firstName = "Meylisa",
            lastName = "Fernanda",
            gender = Gender.FEMALE,
            religion = Religion.PROTESTANT,
            studentId = "221301207",
            major = Major.PSYCHOLOGY
        )

        val responsePost = client.post("/users/role/STUDENT") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )

            setBody(student)
        }
        assertEquals(HttpStatusCode.Created, responsePost.status)

        val newUserSize = client.get("/users").body<List<User>>().size
        assertEquals(initialUserSize + 1, newUserSize)

        val getByEmail = client.get("/users/email/meylisafernanda15@gmail.com")
        val result = getByEmail.body<User>()
        assertEquals(
            result.fullName, "${student.firstName} ${student.lastName}"
        )

    }
}