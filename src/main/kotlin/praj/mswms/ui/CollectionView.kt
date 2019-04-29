/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.*
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
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    private val btnNew: Button                          by fxid()
    private val btnSave: Button                         by fxid()
    private val btnDelete: Button                       by fxid()

    private val collectionModel = object : ItemViewModel<Collection>() {
        val id       = bind(Collection::idProperty)
        val time     = bind(Collection::timeProperty)
        val vehicle  = bind(Collection::vehicleProperty)
        val location = bind(Collection::locationProperty)
        val amount   = bind(Collection::amountProperty)
    }

    private val collectionRepo = RepositoryService.collectionRepository
    private val isNewCollection = SimpleBooleanProperty(false)

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
                get(1).cellFactory = Callback { object : TableCell<Collection, LocalDateTime>() {
                    override fun updateItem(item: LocalDateTime?, empty: Boolean) {
                        super.updateItem(item, empty)
                        text = if (empty) "Unavailable"
                               else item!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))
                    }
                }}
                get(2).cellValueFactory = Callback { it.value.locationProperty() }
                get(3).cellValueFactory = Callback { it.value.vehicleProperty() }
                get(4).cellValueFactory = Callback { it.value.amountProperty() }
            }
            bindSelected(collectionModel)
            items = collectionRepo.elementList
            sortOrder.add(columns[0])
            selectFirst()
        }

        btnNew.disableProperty().bind(isNewCollection)
        fieldId.disableProperty().bind(isNewCollection.not())
        btnSave.disableProperty().bind(collectionModel.dirty.not())
        tableCollection.disableProperty().bind(isNewCollection)
        isNewCollection.addListener { _, _, value ->
            btnDelete.text = if (value) "Cancel" else "Delete"
        }
    }

    fun onNewCollection() {
        isNewCollection.set(true)
        collectionModel.item = Collection(
                0,
                LocalDateTime.now(),
                RepositoryService.locationRepository.elementList[0],
                RepositoryService.vehicleRepository.elementList[0],
                BigDecimal.ZERO
        )
    }

    fun onSaveCollection() {
        collectionModel.commit()
        if (isNewCollection.get()) {
            collectionRepo.addByDAO(collectionModel.item)
            isNewCollection.set(false)
            tableCollection.selectionModel.select(collectionModel.item)
            return
        }
        collectionRepo.updateByDAO(tableCollection.selectedItem?.id ?: return, collectionModel.item)
    }

    fun onDeleteCollection() {
        if (isNewCollection.get()) {
            isNewCollection.set(false)
            tableCollection.selectFirst()
            return
        }
        collectionRepo.deleteByDAO(tableCollection.selectedItem?.id ?: return)
    }
}