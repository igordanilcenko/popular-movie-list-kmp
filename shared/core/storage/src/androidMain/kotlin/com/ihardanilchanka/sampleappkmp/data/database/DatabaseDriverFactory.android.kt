package com.ihardanilchanka.sampleappkmp.data.database

import android.content.Context
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

@Suppress("UNCHECKED_CAST")
actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(schema: SqlSchema<*>, dbName: String): SqlDriver =
        AndroidSqliteDriver(
            schema = schema as SqlSchema<QueryResult.Value<Unit>>,
            context = context,
            name = dbName,
        )
}
