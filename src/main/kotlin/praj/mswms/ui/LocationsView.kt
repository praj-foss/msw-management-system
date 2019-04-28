/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.util.Callback
import javafx.util.converter.IntegerStringConverter
import praj.mswms.data.model.Location
import praj.mswms.service.RepositoryService
import tornadofx.*

/**
 * UI for Locations.
 */
class LocationsView : View("Locations") {
    override val root: AnchorPane by fxml("/fxml/locations-view.fxml")

    private val tableLocation: TableView<Location>  by fxid()
    private val fieldId: TextField                  by fxid()
    private val fieldName: TextField                by fxid()
    private val fieldType: ComboBox<String>         by fxid()
    private val btnNew: Button                      by fxid()
    private val btnSave: Button                     by fxid()
    private val btnDelete: Button                   by fxid()

    private val locationModel = object : ItemViewModel<Location>() {
        val id   = bind(Location::idProperty)
        val name = bind(Location::nameProperty)
        val type = bind(Location::typeProperty)
    }

    private val locationRepo = RepositoryService.locationRepository
    private var isNewLocation = SimpleBooleanProperty(false)

    override fun onDock() {
        fieldId.textProperty().bindBidirectional(locationModel.id, IntegerStringConverter())
        fieldName.textProperty().bindBidirectional(locationModel.name)

        fieldType.valueProperty().bindBidirectional(locationModel.type)
        fieldType.items = FXCollections.observableArrayList<String>().also {
            locationRepo.elementList.forEach { loc ->
                if (! it.contains(loc.type))
                    it.add(loc.type)
            }
        }

        tableLocation.apply {
            columns.apply {
                get(0).cellValueFactory = Callback { it.value.idProperty() }
                get(1).cellValueFactory = Callback { it.value.nameProperty() }
                get(2).cellValueFactory = Callback { it.value.typeProperty() }
            }
            bindSelected(locationModel)
            items = locationRepo.elementList
            sortOrder.add(columns[0])
            selectFirst()
        }

        btnNew.disableProperty().bind(isNewLocation)
        btnSave.disableProperty().bind(locationModel.dirty.not())
        tableLocation.disableProperty().bind(isNewLocation)
        isNewLocation.addListener { _, _, value ->
            btnDelete.text = if (value) "Cancel" else "Delete"
        }
    }

    fun onNewLocation() {
        isNewLocation.set(true)
        locationModel.item = Location(0, "Name", fieldType.items[0])
    }

    fun onSaveLocation() {
        locationModel.commit()
        if (isNewLocation.get()) {
            locationRepo.addByDAO(locationModel.item)
            isNewLocation.set(false)
            tableLocation.selectionModel.select(locationModel.item)
            return
        }
        locationRepo.updateByDAO(tableLocation.selectedItem?.id ?: return, locationModel.item)
    }

    fun onDeleteLocation() {
        if (isNewLocation.get()) {
            isNewLocation.set(false)
            tableLocation.selectFirst()
            return
        }
        locationRepo.deleteByDAO(tableLocation.selectedItem?.id ?: return)
    }
}