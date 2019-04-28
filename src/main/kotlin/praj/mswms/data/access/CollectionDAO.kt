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
import java.sql.Timestamp

/**
 * DAO for Collection objects.
 */
class CollectionDAO : DAO<Collection> {
    private val psGetById: PreparedStatement
    private val psGetAll: PreparedStatement
    private val psInsert: PreparedStatement
    private val psUpdate: PreparedStatement
    private val psDelete: PreparedStatement

    init {
        val connection = DatabaseService.connection

        psGetById = connection.prepareStatement(
                "SELECT * FROM Collection WHERE CollectionID = ?"
        )

        psGetAll = connection.prepareStatement(
                "SELECT * FROM Collection"
        )

        psInsert = connection.prepareStatement(
                "INSERT INTO Collection " +
                "VALUES (?, ?, ?, ?, ?)"
        )

        psUpdate = connection.prepareStatement(
                "UPDATE Collection " +
                "SET CollectionID = ?, CollectionTime = ?, LocationID = ?, VehicleID = ?, Amount = ? " +
                "WHERE CollectionID = ?"
        )

        psDelete = connection.prepareStatement(
                "DELETE FROM Vehicle " +
                "WHERE LocationID = ?"
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

    override fun insert(element: Collection) {
        psInsert.apply {
            clearParameters()
            setInt(1, element.id)
            setTimestamp(2, Timestamp.valueOf(element.time))
            setInt(3, element.location.id)
            setInt(4, element.vehicle.id)
            setBigDecimal(5, element.amount)
            executeUpdate()
        }
    }

    override fun update(id: Int, element: Collection) {
        psUpdate.apply {
            clearParameters()
            setInt(1, element.id)
            setTimestamp(2, Timestamp.valueOf(element.time))
            setInt(3, element.location.id)
            setInt(4, element.vehicle.id)
            setBigDecimal(5, element.amount)
            setInt(6, id)
            executeUpdate()
        }
    }

    override fun delete(id: Int) {
        psDelete.apply {
            clearParameters()
            setInt(1, id)
            executeUpdate()
        }
    }

    private fun newCollection(rs: ResultSet) = Collection(
            id       = rs.getInt(1),
            time     = rs.getTimestamp(2).toLocalDateTime(),
            location = RepositoryService.locationRepository.get(rs.getInt(3))!!,
            vehicle  = RepositoryService.vehicleRepository.get(rs.getInt(4))!!,
            amount   = rs.getBigDecimal(5)
    )
}