/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.access

import praj.mswms.data.model.Collection
import praj.mswms.service.DatabaseService
import praj.mswms.service.RepositoryService
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * DAO for Collection objects.
 */
class CollectionDAO : DAO<Collection> {
    private val psGetById: PreparedStatement
    private val psGetAll: PreparedStatement

    init {
        val connection = DatabaseService.connection

        psGetById = connection.prepareStatement(
                "SELECT * FROM Collection WHERE CollectionID = ?"
        )

        psGetAll = connection.prepareStatement(
                "SELECT * FROM Collection"
        )
    }

    override fun getById(id: Int): Collection? {
        psGetById.clearParameters()
        psGetById.setInt(1, id)

        val rs = psGetById.executeQuery()
        while (rs.next())
            return newCollection(rs)

        return null
    }

    override fun getAll(): List<Collection> {
        val collectionList = ArrayList<Collection>()
        val rs = psGetAll.executeQuery()
        while (rs.next())
            collectionList.add(newCollection(rs))

        return collectionList
    }

    private fun newCollection(rs: ResultSet) = Collection(
            id        = rs.getInt(1),
            time      = rs.getTimestamp(2).toLocalDateTime(),
            location  = RepositoryService.locationRepository.get(rs.getInt(3))!!,
            vehicleId = rs.getInt(4),
            amount    = rs.getBigDecimal(5)
    )
}