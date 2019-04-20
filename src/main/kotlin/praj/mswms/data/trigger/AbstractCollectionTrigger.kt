/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import org.h2.api.Trigger
import java.sql.Connection
import java.sql.PreparedStatement

/**
 * Abstract trigger for Collection table.
 */
abstract class AbstractCollectionTrigger : Trigger {
    private lateinit var psUpdateVehicleLocation: PreparedStatement

    protected val indexCollectionID = 0
    protected val indexTime         = 1
    protected val indexLocationID   = 2
    protected val indexVehicleID    = 3
    protected val indexAmount       = 4

    override fun init(conn: Connection?, schemaName: String?, triggerName: String?, tableName: String?, before: Boolean, type: Int) {
        psUpdateVehicleLocation = conn!!.prepareStatement("UPDATE Vehicle SET LocationID = ?, StatusID = 1 WHERE VehicleID = ?")
    }

    protected fun updateVehicleLocation(vehicleId: Int, locationId: Int) = psUpdateVehicleLocation.run {
        clearParameters()
        setInt(1, locationId)
        setInt(2, vehicleId)
        executeUpdate()
    }

    override fun remove() { }

    override fun close() { }
}