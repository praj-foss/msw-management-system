/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import praj.mswms.data.model.Location
import praj.mswms.service.RepositoryService
import java.sql.Connection

/**
 * Triggered after INSERT on Location table.
 */
class LocationInsertTrigger : AbstractLocationTrigger() {
    companion object {
        val SQL = "CREATE TRIGGER IF NOT EXISTS location_insert " +
                  "AFTER INSERT ON Location " +
                  "FOR EACH ROW CALL \"${LocationInsertTrigger::class.java.name}\""
    }

    override fun fire(conn: Connection?, oldRow: Array<out Any>?, newRow: Array<out Any>?) {
        RepositoryService.locationRepository.add(Location(
                id   = newRow!![indexLocationID] as Int,
                name = newRow[indexName] as String,
                type = getLocationType(newRow[indexTypeID] as Int)
        ))
    }
}