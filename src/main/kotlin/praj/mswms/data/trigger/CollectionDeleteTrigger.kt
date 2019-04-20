/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.trigger

import praj.mswms.service.RepositoryService
import java.sql.Connection

/**
 * Triggered after DELETE on Collection table.
 */
class CollectionDeleteTrigger : AbstractCollectionTrigger() {
    companion object {
        val SQL = "CREATE TRIGGER IF NOT EXISTS collection_delete " +
                  "AFTER DELETE ON Collection " +
                  "FOR EACH ROW CALL \"${CollectionDeleteTrigger::class.java.name}\""
    }

    override fun fire(conn: Connection?, oldRow: Array<out Any>?, newRow: Array<out Any>?) {
        RepositoryService.collectionRepository.remove(oldRow!![indexCollectionID] as Int)
    }
}