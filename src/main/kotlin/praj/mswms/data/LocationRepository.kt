/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import praj.mswms.data.access.LocationDAO
import praj.mswms.data.model.Location

/**
 * Repository for Location objects.
 */
class LocationRepository : Repository<Location>() {
    public override val elementList: ObservableList<Location> = FXCollections.observableArrayList()
    override val dao = LocationDAO()
}