/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ProgressBar
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import praj.mswms.service.RepositoryService
import praj.mswms.util.SQLRunnerService
import tornadofx.View

/**
 * UI for executing SQL statements.
 */
class SQLRunnerView : View("SQL Runner") {
    override val root: VBox              by fxml("/fxml/sql-runner-view.fxml")
    private val fieldDelay: TextField    by fxid()
    private val sqlArea: TextArea        by fxid()
    private val btnRun: Button           by fxid()
    private val btnClear: Button         by fxid()
    private val progressBar: ProgressBar by fxid()

    private val sqlService = SQLRunnerService()

    override fun onBeforeShow() {
        fieldDelay.disableProperty().bind(sqlService.runningProperty())
        btnRun.disableProperty().bind(sqlService.runningProperty())
        btnClear.disableProperty().bind(sqlService.runningProperty())
        sqlArea.disableProperty().bind(sqlService.runningProperty())
        progressBar.disableProperty().bind(sqlService.runningProperty().not())
        progressBar.progressProperty().bind(sqlService.progressProperty())

        sqlService.connect(sqlArea.paragraphs.filtered { it.isNotBlank() })
        sqlService.onSucceeded = EventHandler {
            RepositoryService.lineChartRepository.refresh()
            RepositoryService.barChartRepository.refresh()
        }

        sqlArea.requestFocus()
    }

    fun onRun() {
        val delay = fieldDelay.text.ifBlank {"0"}.toLong()
        sqlService.run(delay)
    }

    fun onClear() {
        sqlArea.clear()
    }
}