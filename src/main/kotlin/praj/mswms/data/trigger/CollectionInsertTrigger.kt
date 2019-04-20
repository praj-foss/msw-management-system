/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import praj.mswms.data.model.Collection
import praj.mswms.service.RepositoryService
import java.math.BigDecimal
import java.sql.Connection
import java.sql.Timestamp

/**
 * Triggered after INSERT on Collection table.
 */
class CollectionInsertTrigger : AbstractCollectionTrigger() {
    companion object {
        val SQL = "CREATE TRIGGER IF NOT EXISTS collection_insert " +
                  "AFTER INSERT ON Collection " +
                  "FOR EACH ROW CALL \"${CollectionInsertTrigger::class.java.name}\""
    }

    override fun fire(conn: Connection?, oldRow: Array<out Any>?, newRow: Array<out Any>?) {
        RepositoryService.collectionRepository.add(Collection(
                id        = newRow!![indexCollectionID] as Int,
                time      = (newRow[indexTime] as Timestamp).toLocalDateTime(),
                location  = RepositoryService.locationRepository.get(newRow[indexLocationID] as Int)!!,
                vehicleId = newRow[indexVehicleID] as Int,
                amount    = newRow[indexAmount] as BigDecimal
        ))
        updateVehicleLocation(newRow[indexVehicleID] as Int, newRow[indexLocationID] as Int)
    }
}