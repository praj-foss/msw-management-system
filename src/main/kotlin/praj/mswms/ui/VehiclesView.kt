/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.beans.property.ObjectProperty
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.util.Callback
import javafx.util.converter.IntegerStringConverter
import praj.mswms.data.model.Location
import praj.mswms.data.model.Vehicle
import praj.mswms.service.RepositoryService
import tornadofx.ItemViewModel
import tornadofx.View
import tornadofx.bindSelected
import tornadofx.selectFirst

/**
 * UI for Vehicles.
 */
class VehiclesView : View("Vehicles") {
    override val root: AnchorPane by fxml("/fxml/vehicles-view.fxml")

    private val tableVehicle: TableView<Vehicle>    by fxid()
    private val fieldId: TextField                  by fxid()
    private val fieldModel: ChoiceBox<String>       by fxid()
    private val fieldLocation: ChoiceBox<ObjectProperty<Location?>>  by fxid()
    private val fieldStatus: ChoiceBox<ObjectProperty<String?>>      by fxid()

    private val vehicleModel = object : ItemViewModel<Vehicle>() {
        val id       = bind(Vehicle::idProperty)
        val model    = bind(Vehicle::modelProperty)
        val location = bind(Vehicle::lastLocationProperty)
        val status   = bind(Vehicle::statusProperty)
    }

    override fun onDock() {
        fieldId.textProperty().bindBidirectional(vehicleModel.id, IntegerStringConverter())
        fieldModel.valueProperty().bindBidirectional(vehicleModel.model)
        fieldLocation.valueProperty().bindBidirectional(vehicleModel.location)
        fieldStatus.valueProperty().bindBidirectional(vehicleModel.status)

        tableVehicle.apply {
            columns.apply {
                get(0).cellValueFactory = Callback { it.value.idProperty() }
                get(1).cellValueFactory = Callback { it.value.modelProperty() }
                get(2).cellValueFactory = Callback { it.value.capacityProperty() }
                get(3).cellValueFactory = Callback { it.value.lastLocationProperty() }
                get(4).cellValueFactory = Callback { it.value.statusProperty() }
            }
            bindSelected(vehicleModel)
            items = RepositoryService.vehicleRepository.elementList
            selectFirst()
        }
    }

    fun onNewVehicle() {}

    fun onSaveVehicle() {}

    fun onDeleteVehicle() {}
}