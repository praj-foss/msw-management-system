/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.service

import praj.mswms.data.trigger.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

/**
 * Service to provide database connection.
 */
object DatabaseService {
    private const val username = "sa"
    private const val password = "sa"
    private const val url      = "jdbc:h2:~/mswms"

    var connection: Connection

    init {
        // Load database driver
        Class.forName("org.h2.Driver")

        // Get connection
        connection = DriverManager.getConnection(url, username, password)
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