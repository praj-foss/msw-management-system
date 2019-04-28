/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.scene.control.ComboBox
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.util.Callback
import javafx.util.converter.BigDecimalStringConverter
import javafx.util.converter.IntegerStringConverter
import jfxtras.scene.control.LocalDateTimeTextField
import praj.mswms.data.model.Collection
import praj.mswms.data.model.Location
import praj.mswms.data.model.Vehicle
import praj.mswms.service.RepositoryService
import tornadofx.*

/**
 * UI for Collection.
 */
class CollectionView : View("Collection") {
    override val root: AnchorPane by fxml("/fxml/collection-view.fxml")

    private val tableCollection: TableView<Collection>  by fxid()
    private val fieldId: TextField                      by fxid()
    private val fieldTime: LocalDateTimeTextField       by fxid()
    private val fieldLocation: ComboBox<Location>       by fxid()
    private val fieldVehicle: ComboBox<Vehicle>         by fxid()
    private val fieldAmount: TextField                  by fxid()

    private val collectionModel = object : ItemViewModel<Collection>() {
        val id       = bind(Collection::idProperty)
        val time     = bind(Collection::timeProperty)
        val vehicle  = bind(Collection::vehicleProperty)
        val location = bind(Collection::locationProperty)
        val amount   = bind(Collection::amountProperty)
    }

    override fun onDock() {
        fieldId.textProperty().bindBidirectional(collectionModel.id, IntegerStringConverter())
        fieldTime.localDateTimeProperty().bindBidirectional(collectionModel.time)

        fieldLocation.valueProperty().bindBidirectional(collectionModel.location)
        fieldLocation.items = RepositoryService.locationRepository.elementList

        fieldVehicle.valueProperty().bindBidirectional(collectionModel.vehicle)
        fieldVehicle.items = RepositoryService.vehicleRepository.elementList

        fieldAmount.textProperty().bindBidirectional(collectionModel.amount, BigDecimalStringConverter())

        tableCollection.apply {
            columns.apply {
                get(0).cellValueFactory = Callback { it.value.idProperty() }
                get(1).cellValueFactory = Callback { it.value.timeProperty() }
                get(2).cellValueFactory = Callback { it.value.locationProperty() }
                get(3).cellValueFactory = Callback { it.value.vehicleProperty() }
                get(4).cellValueFactory = Callback { it.value.amountProperty() }
            }
            bindSelected(collectionModel)
            items = RepositoryService.collectionRepository.elementList
            selectFirst()
        }
    }

    fun onNewCollection() {}

    fun onSaveCollection() { collectionModel.commit() }

    fun onDeleteCollection() {}
}