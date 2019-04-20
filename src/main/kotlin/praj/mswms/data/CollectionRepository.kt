/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import praj.mswms.data.access.CollectionDAO
import praj.mswms.data.model.Collection

/**
 * Repository for Collection objects.
 */
class CollectionRepository : Repository<Collection>() {
    public override val elementList: ObservableList<Collection> = FXCollections.observableArrayList()
    override val dao = CollectionDAO()
}