package com.ihardanilchanka.sampleappkmp.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver

@Suppress("UNCHECKED_CAST")
actual class DatabaseDriverFactory {
    actual fun createDriver(schema: SqlSchema<*>, dbName: String): SqlDriver =
        NativeSqliteDriver(schema, dbName)
}
