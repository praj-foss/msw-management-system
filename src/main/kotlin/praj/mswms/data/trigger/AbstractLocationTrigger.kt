/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import org.h2.api.Trigger
import java.sql.Connection
import java.sql.PreparedStatement

/**
 * Abstract trigger for Location table.
 */
abstract class AbstractLocationTrigger : Trigger {
    private lateinit var psGetLocationType: PreparedStatement

    protected val indexLocationID = 0
    protected val indexName       = 1
    protected val indexTypeID     = 2

    override fun init(conn: Connection?, schemaName: String?, triggerName: String?, tableName: String?, before: Boolean, type: Int) {
        psGetLocationType = conn!!.prepareStatement("SELECT TypeName FROM LocationType WHERE TypeID = ?")
    }

    protected fun getLocationType(typeID: Int): String = psGetLocationType.run {
        clearParameters()
        setInt(1, typeID)
        executeQuery().run {
            next()
            getString("TypeName")
        }
    }

    override fun remove() { }

    override fun close() { }
}