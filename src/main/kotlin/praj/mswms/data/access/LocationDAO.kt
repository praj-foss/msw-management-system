/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.access

import praj.mswms.service.DatabaseService
import praj.mswms.data.model.Location
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * DAO for Location objects.
 */
class LocationDAO : DAO<Location> {
    private val psGetById: PreparedStatement
    private val psGetAll: PreparedStatement
    private val psGetType: PreparedStatement
    private val psInsert: PreparedStatement
    private val psUpdate: PreparedStatement
    private val psDelete: PreparedStatement

    init {
        val connection = DatabaseService.connection

        psGetById = connection.prepareStatement(
                "SELECT L.LocationID, L.Name, T.TypeName " +
                "FROM Location L, LocationType T " +
                "WHERE L.TypeID = T.TypeID AND L.LocationID = ?"
        )

        psGetAll = connection.prepareStatement(
                "SELECT L.LocationID, L.Name, T.TypeName " +
                "FROM Location L, LocationType T " +
                "WHERE L.TypeID = T.TypeID"
        )

        psGetType = connection.prepareStatement(
                "SELECT TypeID " +
                "FROM LocationType " +
                "WHERE TypeName = ?"
        )

        psInsert = connection.prepareStatement(
                "INSERT INTO Location " +
                "VALUES (?, ?, ?)"
        )

        psUpdate = connection.prepareStatement(
                "UPDATE Location " +
                "SET LocationID = ?, Name = ?, TypeID = ? " +
                "WHERE LocationID = ?"
        )

        psDelete = connection.prepareStatement(
                "DELETE FROM Location " +
                "WHERE LocationID = ?"
        )
    }

    override fun getById(id: Int): Location? {
        psGetById.clearParameters()
        psGetById.setInt(1, id)

        val rs = psGetById.executeQuery()
        while (rs.next())
            return newLocation(rs)

        return null
    }

    override fun getAll(): List<Location> {
        val locationList = ArrayList<Location>()
        val rs = psGetAll.executeQuery()
        while (rs.next())
            locationList.add(newLocation(rs))

        return locationList
    }

    override fun insert(element: Location) {
        psInsert.apply {
            clearParameters()
            setInt(1, element.id)
            setString(2, element.name)
            setInt(3, element.getTypeId()!!)
            executeUpdate()
        }
    }

    override fun update(id: Int, element: Location) {
        psUpdate.apply {
            clearParameters()
            setInt(1, element.id)
            setString(2, element.name)
            setInt(3, element.getTypeId()!!)
            setInt(4, id)
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

    private fun Location.getTypeId(): Int? = psGetType.run {
        clearParameters()
        setString(1, type)
        executeQuery().let { if (it.next()) it.getInt(1) else null }
    }

    private fun newLocation(rs: ResultSet) = Location(
            id   = rs.getInt(1),
            name = rs.getString(2),
            type = rs.getString(3)
    )
}