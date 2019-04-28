/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import praj.mswms.service.RepositoryService
import java.math.BigDecimal
import java.sql.Connection
import java.sql.Timestamp

/**
 * Triggered after UPDATE on Collection table.
 */
class CollectionUpdateTrigger : AbstractCollectionTrigger() {
    companion object {
        val SQL = "CREATE TRIGGER IF NOT EXISTS collection_update " +
                  "AFTER UPDATE ON Collection " +
                  "FOR EACH ROW CALL \"${CollectionUpdateTrigger::class.java.name}\""
    }

    override fun fire(conn: Connection?, oldRow: Array<out Any>?, newRow: Array<out Any>?) {
        RepositoryService.collectionRepository.get(oldRow!![indexCollectionID] as Int)?.apply {
            id       = newRow!![indexCollectionID] as Int
            time     = (newRow[indexTime] as Timestamp).toLocalDateTime()
            location = RepositoryService.locationRepository.get(newRow[indexLocationID] as Int)!!
            vehicle  = RepositoryService.vehicleRepository.get(newRow[indexVehicleID] as Int)!!
            amount   = newRow[indexAmount] as BigDecimal
        }
    }
}