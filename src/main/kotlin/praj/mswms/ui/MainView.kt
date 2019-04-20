/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.scene.control.TableView
import javafx.scene.layout.VBox
import javafx.util.Callback
import praj.mswms.data.model.Collection
import praj.mswms.data.model.Vehicle
import praj.mswms.service.RepositoryService
import tornadofx.View

/**
 * The main view.
 */
class MainView : View("Municipal Solid Waste Management System") {
    override val root: VBox by fxml("/fxml/main-view.fxml")
    private val tableVehicles: TableView<Vehicle> by fxid()
    private val tableCollection: TableView<Collection> by fxid()

    override fun onBeforeShow() {
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

        tableVehicles.items = RepositoryService.vehicleRepository.elementList
        tableCollection.items = RepositoryService.collectionRepository.elementList
    }

    fun openSQLRunner() {
        find<SQLRunnerView>().openWindow(owner = null)
    }
}
