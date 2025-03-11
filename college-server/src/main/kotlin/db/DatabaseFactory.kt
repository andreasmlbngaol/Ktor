package com.andreasmlbngaol.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = config.propertyOrNull("postgres.url")?.getString() ?: "jdbc:postgresql://localhost:5432/college_kotlin_db"
            driverClassName = config.propertyOrNull("postgres.driver")?.getString() ?: "org.postgresql.Driver"
            username = config.propertyOrNull("postgres.user")?.getString() ?: "postgres"
            password = config.propertyOrNull("postgres.password")?.getString() ?: "password"
            maximumPoolSize = 5
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        Database.connect(HikariDataSource(hikariConfig))

        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ
    }
}