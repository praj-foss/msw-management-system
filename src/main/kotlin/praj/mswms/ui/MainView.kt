/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.chart.BarChart
import javafx.scene.chart.LineChart
import javafx.scene.chart.XYChart
import javafx.scene.control.Tab
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.util.Callback
import javafx.util.converter.IntegerStringConverter
import praj.mswms.data.model.Collection
import praj.mswms.data.model.Location
import praj.mswms.data.model.Vehicle
import praj.mswms.service.RepositoryService
import praj.mswms.util.PDFExportService
import tornadofx.*
import java.math.BigDecimal

/**
 * The main view.
 */
class MainView : View("Municipal Solid Waste Management System") {
    override val root: VBox by fxml("/fxml/main-view.fxml")

    private val tabOverview: Tab    by fxid()
    private val tabLocations: Tab   by fxid()
    private val tabVehicles: Tab    by fxid()
    private val tabCollection: Tab  by fxid()

    private val pdfService = PDFExportService()

    override fun onDock() {
        tabOverview.content   = find<OverviewView>().root
        tabLocations.content  = find<LocationsView>().root
        tabVehicles.content   = find<VehiclesView>().root
        tabCollection.content = find<CollectionView>().root
    }

    fun openSQLRunner() {
        find<SQLRunnerView>().openWindow(owner = null)
    }

    fun generateReport() {
        val path = FileChooser().apply {
            initialFileName = "Report.pdf"
            extensionFilters.add(FileChooser.ExtensionFilter("PDF Files", "*.pdf"))
        }.showSaveDialog(currentStage) ?: return

        pdfService.onSucceeded = EventHandler { println("Saved to ${path.absolutePath}") }
        pdfService.generate(path, find<OverviewView>().getSnapshot())
    }
}
