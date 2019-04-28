/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import praj.mswms.data.model.Location
import praj.mswms.data.model.Vehicle
import praj.mswms.service.RepositoryService
import java.sql.Connection

/**
 * Triggered after UPDATE on Vehicle table.
 */
class VehicleUpdateTrigger : AbstractVehicleTrigger() {
    companion object {
        val SQL = "CREATE TRIGGER IF NOT EXISTS vehicle_update " +
                  "AFTER UPDATE ON Vehicle " +
                  "FOR EACH ROW CALL \"${VehicleUpdateTrigger::class.java.name}\""
    }

    override fun fire(conn: Connection?, oldRow: Array<out Any>?, newRow: Array<out Any>?) {
        val (_model, _capacity) = getVehicleModelAndCapacity(newRow!![indexModelID] as Int)

        praj.mswms.service.RepositoryService.vehicleRepository.get(oldRow!![indexVehicleID] as Int)?.apply {
            id = newRow[indexVehicleID] as Int
            model = _model
            capacity = _capacity
            lastLocation = RepositoryService.locationRepository.get(newRow[indexLocationID] as Int) ?: Location.UNAVAILABLE
            status = getVehicleStatus(newRow[indexStatusID] as Int) ?: Vehicle.UNAVAILABLE.status
        }
    }
}