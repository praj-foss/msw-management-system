/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.scene.control.ChoiceBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import praj.mswms.data.model.Collection
import praj.mswms.data.model.Location
import praj.mswms.data.model.Vehicle
import tornadofx.ItemViewModel
import tornadofx.View

/**
 * UI for Collection.
 */
class CollectionView : View("Collection") {
    override val root: AnchorPane by fxml("/fxml/collection-view.fxml")

    private val tableCollection: TableView<Collection>  by fxid()
    private val fieldId: TextField                      by fxid()
    private val fieldDate: DatePicker                   by fxid()
    private val fieldHours: TextField                   by fxid()
    private val fieldMinutes: TextField                 by fxid()
    private val fieldSeconds: TextField                 by fxid()
    private val fieldAm: ChoiceBox<String>              by fxid()
    private val fieldLocation: ChoiceBox<Location>      by fxid()
    private val fieldVehicle: ChoiceBox<Vehicle>        by fxid()
    private val fieldAmount: TextField                  by fxid()

    private val collectionModel = object : ItemViewModel<Collection>() {
        val id        = bind(Collection::idProperty)
        val time      = bind(Collection::timeProperty)
        val location  = bind(Collection::locationProperty)
        val vehicleId = bind(Collection::vehicleIdProperty)
        val amount    = bind(Collection::amountProperty)
    }

    override fun onDock() {
        // TODO: Setup fields
    }

    fun onNewCollection() {}

    fun onSaveCollection() {}

    fun onDeleteCollection() {}
}