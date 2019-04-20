/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import praj.mswms.data.access.DAO
import praj.mswms.data.access.VehicleDAO
import praj.mswms.data.model.Vehicle
import tornadofx.Controller

/**
 * Repository for Vehicle objects.
 */
class VehicleRepository : Repository<Vehicle>() {
    public override val elementList: ObservableList<Vehicle> = FXCollections.observableArrayList()
    override val dao = VehicleDAO()
}