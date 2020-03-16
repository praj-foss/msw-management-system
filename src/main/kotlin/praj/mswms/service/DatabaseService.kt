/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.service

import praj.mswms.data.trigger.*
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

/**
 * Service to provide database connection.
 */
object DatabaseService {
    private const val username = "sa"
    private const val password = "sa"
    private const val appHome  = ".mswms"
    private const val dbName   = "local"

    var connection: Connection

    init {
        // Setup app dir
        val appDir = Paths.get(System.getProperty("user.home"), appHome)
        val firstRun = Files.notExists(appDir.resolve("$dbName.mv.db"))

        // Load database driver
        Class.forName("org.h2.Driver")

        // Get connection
        val dbUrl = "jdbc:h2:${appDir.resolve(dbName)}"
        connection = DriverManager.getConnection(dbUrl, username, password)

        // First run setup
        if (firstRun) {
            val initSql = this::class.java.classLoader.getResource("sql/init.sql")?.readText()
            connection.prepareStatement(initSql).execute()
        }
    }

    fun start() {
        connection.createStatement().run {
            initTriggers(this)
            close()
        }
        println("Database service started")
    }

    private fun initTriggers(stat: Statement) {
        arrayListOf(
                // Location triggers
                LocationInsertTrigger.SQL, LocationUpdateTrigger.SQL, LocationDeleteTrigger.SQL,

                // Vehicle triggers
                VehicleInsertTrigger.SQL, VehicleUpdateTrigger.SQL, VehicleDeleteTrigger.SQL,

                // Collection triggers
                CollectionInsertTrigger.SQL, CollectionUpdateTrigger.SQL, CollectionDeleteTrigger.SQL
        ).forEach {
            stat.execute(it)
        }
    }

    fun stop() = connection.close()
}