/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.collections.FXCollections
import javafx.scene.control.ComboBox
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

    private val tableVehicle: TableView<Vehicle>   by fxid()
    private val fieldId: TextField                 by fxid()
    private val fieldModel: ComboBox<String>       by fxid()
    private val fieldLocation: ComboBox<Location>  by fxid()
    private val fieldStatus: ComboBox<String>      by fxid()

    private val vehicleModel = object : ItemViewModel<Vehicle>() {
        val id       = bind(Vehicle::idProperty)
        val model    = bind(Vehicle::modelProperty)
        val location = bind(Vehicle::lastLocationProperty)
        val status   = bind(Vehicle::statusProperty)
    }

    override fun onDock() {
        fieldId.textProperty().bindBidirectional(vehicleModel.id, IntegerStringConverter())

        fieldModel.valueProperty().bindBidirectional(vehicleModel.model)
        fieldModel.items = FXCollections.observableArrayList<String>().also {
            RepositoryService.vehicleRepository.elementList.forEach { v ->
                if (! it.contains(v.model))
                    it.add(v.model)
            }
        }

        fieldLocation.valueProperty().bindBidirectional(vehicleModel.location)
        fieldLocation.items = RepositoryService.locationRepository.elementList

        fieldStatus.valueProperty().bindBidirectional(vehicleModel.status)
        fieldStatus.items = FXCollections.observableArrayList<String>().also {
            RepositoryService.vehicleRepository.elementList.forEach { v ->
                if (! it.contains(v.status))
                    it.add(v.status)
            }
        }

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

    fun onSaveVehicle() { vehicleModel.commit() }

    fun onDeleteVehicle() {}
}