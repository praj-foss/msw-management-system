/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.util

import javafx.collections.ObservableList
import javafx.concurrent.Service
import javafx.concurrent.Task
import praj.mswms.service.DatabaseService

/**
 * A JavaFX Service used to execute SQL commands from SQLRunnerView.
 */
class SQLRunnerService : Service<Boolean>() {
    private var firstRun: Boolean = true
    private lateinit var commands: ObservableList<CharSequence>
    private var delay: Long = 0

    private val connection = DatabaseService.connection

    fun connect(list: ObservableList<CharSequence>) {
        commands = list
    }

    fun run(delay: Long) {
        this.delay = delay

        if (firstRun) {
            firstRun = false
            start()
        } else
            restart()
    }

    override fun createTask(): Task<Boolean> {
        return object : Task<Boolean>() {
            override fun call(): Boolean {
                val total = commands.size.toLong()
                var counter = 0L

                val statement = connection.createStatement()
                commands.forEach {
                    statement.execute(it.toString())
                    Thread.sleep(delay)
                    updateProgress(++counter, total)
                }
                statement.close()

                return true
            }

            override fun failed() {
                println("ERROR: SQL execution failed")
            }
        }
    }
}