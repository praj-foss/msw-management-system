/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import praj.mswms.service.RepositoryService
import java.sql.Connection

/**
 * Triggered after DELETE on Location table.
 */
class LocationDeleteTrigger : AbstractLocationTrigger() {
    companion object {
        val SQL = "CREATE TRIGGER IF NOT EXISTS location_delete " +
                  "AFTER DELETE ON Location " +
                  "FOR EACH ROW CALL \"${LocationDeleteTrigger::class.java.name}\""
    }

    override fun fire(conn: Connection?, oldRow: Array<out Any>?, newRow: Array<out Any>?) {
        RepositoryService.locationRepository.delete(oldRow!![indexLocationID] as Int)
    }
}