/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import praj.mswms.service.RepositoryService
import java.sql.Connection

/**
 * Triggered after DELETE on Vehicle table.
 */
class VehicleDeleteTrigger : AbstractVehicleTrigger() {
    companion object {
        val SQL = "CREATE TRIGGER IF NOT EXISTS vehicle_delete " +
                  "AFTER DELETE ON Vehicle " +
                  "FOR EACH ROW CALL \"${VehicleDeleteTrigger::class.java.name}\""
    }

    override fun fire(conn: Connection?, oldRow: Array<out Any>?, newRow: Array<out Any>?) {
        RepositoryService.vehicleRepository.delete(oldRow!![indexVehicleID] as Int)
    }
}