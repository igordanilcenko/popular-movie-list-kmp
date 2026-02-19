package com.ihardanilchanka.sampleappkmp.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

expect class DatabaseDriverFactory {
    fun createDriver(schema: SqlSchema<*>, dbName: String): SqlDriver
}
