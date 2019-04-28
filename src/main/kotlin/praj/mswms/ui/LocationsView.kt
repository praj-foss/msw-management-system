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
import praj.mswms.service.RepositoryService
import tornadofx.ItemViewModel
import tornadofx.View
import tornadofx.bindSelected
import tornadofx.selectFirst

/**
 * UI for Locations.
 */
class LocationsView : View("Locations") {
    override val root: AnchorPane by fxml("/fxml/locations-view.fxml")

    private val tableLocation: TableView<Location>  by fxid()
    private val fieldId: TextField                  by fxid()
    private val fieldName: TextField                by fxid()
    private val fieldType: ComboBox<String>         by fxid()

    private val locationModel = object : ItemViewModel<Location>() {
        val id   = bind(Location::idProperty)
        val name = bind(Location::nameProperty)
        val type = bind(Location::typeProperty)
    }

    override fun onDock() {
        fieldId.textProperty().bindBidirectional(locationModel.id, IntegerStringConverter())
        fieldName.textProperty().bindBidirectional(locationModel.name)

        fieldType.valueProperty().bindBidirectional(locationModel.type)
        fieldType.items = FXCollections.observableArrayList<String>().also {
            RepositoryService.locationRepository.elementList.forEach { loc ->
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
            items = RepositoryService.locationRepository.elementList
            selectFirst()
        }
    }

    fun onNewLocation() {
        // TODO: Send new location to repo
    }

    fun onSaveLocation() {
        locationModel.commit()
        // TODO: Modify location in repo
    }

    fun onDeleteLocation() {
        // TODO: Delete location in repo
    }
}