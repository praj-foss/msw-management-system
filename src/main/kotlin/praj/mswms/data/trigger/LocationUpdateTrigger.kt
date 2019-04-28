/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import praj.mswms.data.model.Location
import praj.mswms.service.RepositoryService
import java.sql.Connection

/**
 * Triggered after UPDATE on Location table.
 */
class LocationUpdateTrigger : AbstractLocationTrigger() {
    companion object {
        val SQL = "CREATE TRIGGER IF NOT EXISTS location_update " +
                  "AFTER UPDATE ON Location " +
                  "FOR EACH ROW CALL \"${LocationUpdateTrigger::class.java.name}\""
    }

    override fun fire(conn: Connection?, oldRow: Array<out Any>?, newRow: Array<out Any>?) {
        RepositoryService.locationRepository.update(oldRow!![indexLocationID] as Int, Location(
                id   = newRow!![indexLocationID] as Int,
                name = newRow[indexName] as String,
                type = getLocationType(newRow[indexTypeID] as Int)
        ))
    }
}