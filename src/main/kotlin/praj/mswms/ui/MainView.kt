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
import javafx.scene.control.TableView
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.util.Callback
import praj.mswms.data.model.Collection
import praj.mswms.data.model.Location
import praj.mswms.data.model.Vehicle
import praj.mswms.service.RepositoryService
import praj.mswms.util.PDFExportService
import tornadofx.View
import java.math.BigDecimal

/**
 * The main view.
 */
class MainView : View("Municipal Solid Waste Management System") {
    override val root: VBox by fxml("/fxml/main-view.fxml")

    private val collectionPerDate: LineChart<String, BigDecimal> by fxid()
    private val collectionPerLocation: BarChart<String, BigDecimal> by fxid()

    private val tableLocation: TableView<Location> by fxid()
    private val tableVehicles: TableView<Vehicle> by fxid()
    private val tableCollection: TableView<Collection> by fxid()

    private val pdfService = PDFExportService()

    override fun onBeforeShow() {
        tableLocation.columns.apply {
            get(0).cellValueFactory = Callback { it.value.idProperty() }
            get(1).cellValueFactory = Callback { it.value.nameProperty() }
            get(2).cellValueFactory = Callback { it.value.typeProperty() }
        }

        tableVehicles.columns.apply {
            get(0).cellValueFactory = Callback { it.value.idProperty() }
            get(1).cellValueFactory = Callback { it.value.modelProperty() }
            get(2).cellValueFactory = Callback { it.value.capacityProperty() }
            get(3).cellValueFactory = Callback { it.value.lastLocationProperty() }
            get(4).cellValueFactory = Callback { it.value.statusProperty() }
        }

        tableCollection.columns.apply {
            get(0).cellValueFactory = Callback { it.value.idProperty() }
            get(1).cellValueFactory = Callback { it.value.timeProperty() }
            get(2).cellValueFactory = Callback { it.value.locationProperty() }
            get(3).cellValueFactory = Callback { it.value.vehicleIdProperty() }
            get(4).cellValueFactory = Callback { it.value.amountProperty() }
        }

        collectionPerDate.data = FXCollections.observableArrayList(
                XYChart.Series<String, BigDecimal>().apply {
                    name = "2019"
                    data = RepositoryService.lineChartRepository.listData
                }
        )
        collectionPerLocation.data = FXCollections.observableArrayList(
                XYChart.Series<String, BigDecimal>().apply {
                    name = "2019"
                    data = RepositoryService.barChartRepository.listData
                }
        )
        tableLocation.items = RepositoryService.locationRepository.elementList
        tableVehicles.items = RepositoryService.vehicleRepository.elementList
        tableCollection.items = RepositoryService.collectionRepository.elementList
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
        pdfService.generate(path)
    }
}
