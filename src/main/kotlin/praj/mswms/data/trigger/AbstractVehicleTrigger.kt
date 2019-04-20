/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import org.h2.api.Trigger
import java.sql.Connection
import java.sql.PreparedStatement

/**
 * Abstract trigger for Vehicle table.
 */
abstract class AbstractVehicleTrigger : Trigger {
    private lateinit var psGetVehicleModel: PreparedStatement
    private lateinit var psGetVehicleStatus: PreparedStatement

    protected val indexVehicleID  = 0
    protected val indexModelID    = 1
    protected val indexLocationID = 2
    protected val indexStatusID   = 3

    override fun init(conn: Connection?, schemaName: String?, triggerName: String?, tableName: String?, before: Boolean, type: Int) {
        psGetVehicleModel = conn!!.prepareStatement("SELECT ModelName, Capacity FROM VehicleModel WHERE ModelID = ?")
        psGetVehicleStatus  = conn.prepareStatement("SELECT StatusValue FROM VehicleStatus WHERE StatusID = ?")
    }

    protected fun getVehicleModelAndCapacity(modelId: Int) = psGetVehicleModel.run {
        clearParameters()
        setInt(1, modelId)
        executeQuery().run {
            next()
            Pair<String, Int>(getString("ModelName"), getInt("Capacity"))
        }
    }

    protected fun getVehicleStatus(statusId: Int): String? = psGetVehicleStatus.run {
        clearParameters()
        setInt(1, statusId)
        executeQuery().run {
            next()
            getString("StatusValue")
        }
    }

    override fun remove() { }

    override fun close() { }
}